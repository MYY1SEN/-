package com.myq.service;

import com.myq.internalcommon.constatnt.CommonStatusEnum;
import com.myq.internalcommon.dto.PriceRule;
import com.myq.internalcommon.dto.ResponseResult;
import com.myq.internalcommon.request.foreCastPriceDTO;
import com.myq.internalcommon.response.DirectionResponse;
import com.myq.internalcommon.response.ForCastPriceResponse;
import com.myq.mapper.PriceRuleMapper;
import com.myq.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ForeCastPriceService {
    @Autowired
    private ServiceMapClient serviceMapClient;
    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult<ForCastPriceResponse> foreCastPrice(String depLongitude,
                                                              String depLatitude,
                                                              String destLongitude,
                                                              String destLatitude) {
        log.info("出发地经度:" + depLongitude);
        log.info("出发地纬度:" + depLatitude);
        log.info("目的地经度:" + destLongitude);
        log.info("目的地纬度:" + destLatitude);

        log.info("调用地图服务，查询举例和时长");
        foreCastPriceDTO foreCastPriceDTO = new foreCastPriceDTO();
        foreCastPriceDTO.setDepLatitude(depLatitude);
        foreCastPriceDTO.setDestLatitude(destLatitude);
        foreCastPriceDTO.setDepLongitude(depLongitude);
        foreCastPriceDTO.setDestLongitude(destLongitude);
        ResponseResult<DirectionResponse> direction = serviceMapClient.direction(foreCastPriceDTO);
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDuration();
        log.info("距离" + distance);
        log.info("时长" + duration);

        log.info("读取计价规则");
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("city_code", "110000");
        queryMap.put("vehicle_type", "1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(queryMap);
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(), CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离，时长，计价规则，计算价格");
        double price = getPrice(distance, duration, priceRule);

        ForCastPriceResponse forCastPriceResponse = new ForCastPriceResponse();
        forCastPriceResponse.setPrice(price);
        return ResponseResult.success(forCastPriceResponse);
    }

    /**
     * 计算价格
     *
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private static double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        BigDecimal price = new BigDecimal(0);
        //起步价
        Double startFare = priceRule.getStartFare();
        BigDecimal startFareDecimal = new BigDecimal(startFare);
        price.add(startFareDecimal);

        //里程费
        //里程
        BigDecimal distanceDecimal = new BigDecimal(distance);
        //总里程
        BigDecimal distanceMileDecimal = distanceDecimal.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
        //起步里程
        Double startMile = priceRule.getStartFare();
        BigDecimal startMileDecimal = new BigDecimal(startMile);
        //减法
        double distanceSubtract = distanceMileDecimal.subtract(startMileDecimal).doubleValue();
        double mile = distanceSubtract < 0 ? 0 : distanceSubtract;
        BigDecimal mileDecimail = new BigDecimal(mile);
        //计程单价
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        BigDecimal unitPricePerMileDecimal = new BigDecimal(unitPricePerMile);
        //里程价格
        BigDecimal mileFare = mileDecimail.multiply(unitPricePerMileDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
        price.add(mileFare);

        //时长费
        BigDecimal time = new BigDecimal(duration);
        //分钟
        BigDecimal timeDecimal = time.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
        Double unitPircePerMinute = priceRule.getUnitPircePerMinute();
        BigDecimal unitPricePerMinuteDecimal = new BigDecimal(unitPircePerMinute);
        BigDecimal timeFare = timeDecimal.multiply(unitPricePerMinuteDecimal);

        price.add(timeFare);
        return price.doubleValue();
    }
}
