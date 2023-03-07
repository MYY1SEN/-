package com.myq.service;

import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.response.DirectionResponse;
import com.myq.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {
    @Autowired
    private MapDirectionClient mapDirectionClient;

    public ResponseResult driving(String depLongitude,
                                  String depLatitude,
                                  String destLongitude,
                                  String destLatitude) {
        //调用地图接口
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        return ResponseResult.success(direction);
    }
}
