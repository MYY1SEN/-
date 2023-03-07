package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictController {
    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDistrict(String keywords) {
        return dicDistrictService.initDicDistrict(keywords);
    }
}
