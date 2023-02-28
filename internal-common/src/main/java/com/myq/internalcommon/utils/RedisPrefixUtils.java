package com.myq.internalcommon.utils;

public class RedisPrefixUtils {
    //前缀
    public static String verificationCodePrefix = "passenger-verification-code-";
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号生成key
     *
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 根据手机号和标识生成token
     *
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone, String identity, String tokenType) {
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;
    }

}
