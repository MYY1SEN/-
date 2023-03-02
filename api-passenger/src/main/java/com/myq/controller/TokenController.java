package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.response.TokenResponse;
import com.myq.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {
        ResponseResult responseResult = tokenService.refreshToken(tokenResponse.getRefreshToken());
        System.out.println("原来的refreshToken" + responseResult);
        return responseResult;
    }
}
