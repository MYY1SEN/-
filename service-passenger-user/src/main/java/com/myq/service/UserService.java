package com.myq.service;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.VerficationCodeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone){
        System.out.println("user service");
        //根据手机号查询用户信息

        //判断是否存在

        //不存在插入
        return ResponseResult.success();
    }
}
