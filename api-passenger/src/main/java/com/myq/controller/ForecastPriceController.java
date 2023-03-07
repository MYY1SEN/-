package com.myq.controller;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.foreCastPriceDTO;
import com.myq.service.ForeCastPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ForecastPriceController {
    @Autowired
    private ForeCastPriceService foreCastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult foreCastPrice(@RequestBody foreCastPriceDTO foreCastPriceDTO) {
        String destLatitude = foreCastPriceDTO.getDestLatitude();
        String destLongitude = foreCastPriceDTO.getDestLongitude();
        String depLatitude = foreCastPriceDTO.getDepLatitude();
        String depLongitude = foreCastPriceDTO.getDepLongitude();

        log.info("出发地经度:" + depLongitude);
        log.info("出发地纬度:" + depLatitude);
        log.info("目的地经度:" + destLongitude);
        log.info("目的地纬度:" + destLatitude);
        return foreCastPriceService.foreCastPrice(depLongitude, depLatitude, destLongitude, destLatitude);
    }

}
