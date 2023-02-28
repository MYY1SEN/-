package com.myq.service;

import com.myq.dto.PassengerUser;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        //根据手机号查询用户信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        //判断是否存在
        if (passengerUsers.size() == 0) {
            //不存在插入
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerName("马烨乾");
            passengerUser.setState((byte) 0);
            passengerUser.setPassengerGender((byte) 1);
            Date date = new Date();
            Timestamp now = new Timestamp(date.getTime());
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success();
    }
}
