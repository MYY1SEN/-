package com.myq.internalcommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myq.internalcommon.dto.TokenResult;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "CPFmsb!@#$$";

    private static String JWT_KEY_PHONE = "Phone";

    //身份，乘客 1 ，司机 2
    private static String JWT_KEY_IDENTITY = "identity";

    //生成token
    public static String generatorToken(String passengerPhone, String identity) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);

        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        //整合map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        //过期时间
        builder.withExpiresAt(date);
        //生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    //解析toke
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).toString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).toString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    //测试
    public static void main(String[] args) {
        String s = generatorToken("18062646393","1");
        System.out.println("生成的Token: " + s);
        TokenResult s1 = parseToken(s);
        System.out.println("解析的Token: " + s1);
    }
}
