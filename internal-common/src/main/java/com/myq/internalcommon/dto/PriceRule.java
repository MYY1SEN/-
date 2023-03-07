package com.myq.internalcommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    private String cityCode;
    private String vehicleType;
    private Double startFare;
    private Integer startMile;
    private Double unitPricePerMile;
    private Double unitPircePerMinute;
    private Integer faceVersion;
    private String faceType;
}
