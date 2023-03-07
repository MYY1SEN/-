package com.myq.service;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.foreCastPriceDTO;
import com.myq.internalcommon.response.ForCastPriceResponse;
import com.myq.remote.ServicePriceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForeCastPriceService {
    @Autowired
    private ServicePriceClient servicePriceClient;

    /**
     * 出发地 目的地经纬度
     *
     * @param destLatitude
     * @param destLongitude
     * @param depLatitude
     * @param depLongitude
     * @return
     */
    public ResponseResult foreCastPrice(String depLongitude,
                                        String depLatitude,
                                        String destLongitude,
                                        String destLatitude) {
        log.info("出发地经度:" + depLongitude);
        log.info("出发地纬度:" + depLatitude);
        log.info("目的地经度:" + destLongitude);
        log.info("目的地纬度:" + destLatitude);

        log.info("调用计价服务，计算价格");
        foreCastPriceDTO foreCastPriceDTO = new foreCastPriceDTO();
        foreCastPriceDTO.setDepLongitude(depLongitude);
        foreCastPriceDTO.setDepLatitude(depLatitude);
        foreCastPriceDTO.setDestLatitude(destLatitude);
        foreCastPriceDTO.setDestLongitude(destLongitude);
        ResponseResult<ForCastPriceResponse> foreCast = servicePriceClient.foreCast(foreCastPriceDTO);
        double price = foreCast.getData().getPrice();

        ForCastPriceResponse forCastPriceResponse = new ForCastPriceResponse();
        forCastPriceResponse.setPrice(price);
        return ResponseResult.success(forCastPriceResponse);
    }
}
