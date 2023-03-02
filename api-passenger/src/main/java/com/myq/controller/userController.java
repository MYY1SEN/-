package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class userController {
    @Autowired
    private userService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request) {
        //从http请求中获取accessToken
        String accessToken = request.getHeader("Authorization");
        //根据accessToken查询
        return userService.getUser(accessToken);
    }
}
