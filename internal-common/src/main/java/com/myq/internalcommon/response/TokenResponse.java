package com.myq.internalcommon.response;

import lombok.Data;

@Data
public class TokenResponse {
    private String accesstoken;
    private String refreshToken;
}
