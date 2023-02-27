package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.VerficationCodeDTO;
import com.myq.service.VerficationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerficationCodeController {
    @Autowired
    private VerficationService verficationService;

    @GetMapping("/verfication-code")
    public ResponseResult VerfiCationCode(@RequestBody VerficationCodeDTO verficationCodeDTO) {
        String passengerPhone = verficationCodeDTO.getPassengerPhone();
        return verficationService.generatorCode(passengerPhone);
    }

    @PostMapping("/verfication-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerficationCodeDTO verficationCodeDTO) {
        String passengerPhone = verficationCodeDTO.getPassengerPhone();
        String verificationCode = verficationCodeDTO.getVerificationCode();
        System.out.println("手机号:" + passengerPhone + "验证码:" + verificationCode);
        return verficationService.checkCode(passengerPhone, verificationCode);
    }
}
