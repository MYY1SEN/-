package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.apache.coyote.Response;
import org.apache.http.protocol.ResponseServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numbercode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        //获取随机数
        double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        int result1 = (int) mathRandom;
        System.out.println("发出的验证码为: "+result1);

        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(result1);
        return ResponseResult.success(numberCodeResponse);
    }
}
