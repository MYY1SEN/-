package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10715
 */
@RestController
public class NumberCodeController {
    @GetMapping("/numbercode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        //获取随机数
        double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        int result1 = (int) mathRandom;
        System.out.println("numbercode: " + result1);

        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(result1);
        return ResponseResult.success(numberCodeResponse);
    }
}
