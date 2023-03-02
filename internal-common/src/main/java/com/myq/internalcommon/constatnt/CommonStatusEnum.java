package com.myq.internalcommon.constatnt;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CommonStatusEnum {
    VERIFICATION_CODE_ERROR(1099, "验证码错误"),
    //toke类提示
    TOKEN_ERROR(1199, "TOKEN错误"),
    //用户提示
    USER_NOT_EXIST(1200, "当前用户不存在"),
    //成功
    SUCCESS(1, "success"),
    //失败
    FAIL(0, "fail");


    private Integer code;
    private String value;
}
