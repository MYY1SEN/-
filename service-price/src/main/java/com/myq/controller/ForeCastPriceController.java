package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.foreCastPriceDTO;
import com.myq.internalcommon.response.ForCastPriceResponse;
import com.myq.service.ForeCastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForeCastPriceController {
    @Autowired
    private ForeCastPriceService foreCastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult<ForCastPriceResponse> forecastPrice(@RequestBody foreCastPriceDTO foreCastPriceDTO) {
        String depLongitude = foreCastPriceDTO.getDepLongitude();
        String depLatitude = foreCastPriceDTO.getDepLatitude();
        String destLongitude = foreCastPriceDTO.getDestLongitude();
        String destLatitude = foreCastPriceDTO.getDestLatitude();
        return foreCastPriceService.foreCastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
    }
}
