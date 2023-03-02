package com.myq.service;

import com.myq.internalcommon.constatnt.CommonStatusEnum;
import com.myq.internalcommon.constatnt.IdentityConstant;
import com.myq.internalcommon.constatnt.TokenConstants;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.VerficationCodeDTO;
import com.myq.internalcommon.response.NumberCodeResponse;
import com.myq.internalcommon.response.TokenResponse;
import com.myq.internalcommon.utils.JwtUtils;
import com.myq.internalcommon.utils.RedisPrefixUtils;
import com.myq.remote.ServicePassengerUserClient;
import com.myq.remote.ServiceVerificationcodeClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerficationService {

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     *
     * @param passengerCode
     * @return
     */
    public ResponseResult generatorCode(String passengerCode) {
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> response = serviceVerificationcodeClient.getNumberCode(6);
        int numbercode = response.getData().getNumberCode();
        //存入redis
        //key,value过期时间
        //String key = verificationCodePrefix + passengerCode;
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerCode);
        stringRedisTemplate.opsForValue().set(key, numbercode + "", 2, TimeUnit.MINUTES);
        //通过短信服务器，发送验证码到手机

        //返回值
        return ResponseResult.success();
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //根据手机号去redis读取验证码
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);

        //根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis的值:" + codeRedis);

        //校验验证码
        if (StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        //判断原来是否有用户，并进行处理
        VerficationCodeDTO verficationCodeDTO = new VerficationCodeDTO();
        verficationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verficationCodeDTO);

        //颁发令牌
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        //将token存入redis
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccesstoken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}

