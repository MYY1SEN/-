package com.myq.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.myq.internalcommon.constatnt.TokenConstants;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.dto.TokenResult;
import com.myq.internalcommon.utils.JwtUtils;
import com.myq.internalcommon.utils.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token time out";
            result = false;
        } catch (AlgorithmMismatchException e) {
            resultString = "token AlgorithmMismatchException";
            return false;
        } catch (Exception e) {
            resultString = "token invalid";
            return false;
        }
        if (tokenResult == null) {
            resultString = "token invalid";
            return false;
        } else {
            //从redis取出token
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)) {
                resultString = "token invalid";
                return false;
            } else {
                //比较token
                if (!token.trim().equals(tokenRedis.trim())) {
                    resultString = "token invalid";
                    return false;
                }
            }
        }
        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.println(JSONObject.fromObject(ResponseResult.fail(resultString).toString()));
        }
        return result;
    }
}
