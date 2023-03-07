package com.myq.remote;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.foreCastPriceDTO;
import com.myq.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    public ResponseResult<DirectionResponse> direction(@RequestBody foreCastPriceDTO foreCastPriceDTO);
}
