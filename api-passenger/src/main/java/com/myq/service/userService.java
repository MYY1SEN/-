package com.myq.service;

import com.myq.internalcommon.dto.PassengerUser;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.dto.TokenResult;
import com.myq.internalcommon.request.VerficationCodeDTO;
import com.myq.internalcommon.utils.JwtUtils;
import com.myq.remote.ServicePassengerUserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class userService {
    @Autowired
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUser(String accessToken) {
        log.info("accessToken:" + accessToken);
        //解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        log.info("手机号:" + phone);
        //根据手机号查询用户信息
        ResponseResult userByPhone = servicePassengerUserClient.getUserByPhone(phone);
        return ResponseResult.success(userByPhone.getData());
    }
}
