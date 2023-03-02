package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.VerficationCodeDTO;
import com.myq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class userController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerficationCodeDTO verficationCodeDTO) {
        String passengerPhone = verficationCodeDTO.getPassengerPhone();
        System.out.println("手机号" + passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone) {
        return userService.getUser(passengerPhone);
    }
}
