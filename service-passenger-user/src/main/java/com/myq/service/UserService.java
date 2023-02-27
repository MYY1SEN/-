package com.myq.service;

import com.myq.dto.PassengerUser;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service");
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        //System.out.println(passengerUsers == null ? "无记录" : passengerUsers.get(0).getPassengerPhone());
        //判断是否存在
        if (passengerUsers.size() == 0) {
            //不存在插入
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerName("马烨乾");
            passengerUser.setState((byte) 0);
            passengerUser.setPassengerGender((byte) 1);
            LocalTime now = LocalTime.now();
//            passengerUser.setGmtCreate(now);
//            passengerUser.setGmtModified(now);
            passengerUserMapper.insert(passengerUser);
            System.out.println(now);
        }

        return ResponseResult.success();
    }
}
