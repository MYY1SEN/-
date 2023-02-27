package com.myq.remote;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-verficationCode")
public interface ServiceVerificationcodeClient {
    @RequestMapping(method = RequestMethod.GET,value = "/numbercode/{size}")
    ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);
}
