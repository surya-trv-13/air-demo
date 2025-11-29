package com.panunited.airdemo.service;

import com.panunited.airdemo.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderAutoCalculateService {

    public static Double getHourlyRequirement(Integer intervals , Double orderQuantity) {
        if (intervals == null || orderQuantity == null || intervals <= 0 || orderQuantity <= 0) return 0.0;

        int numberOfTruck = (int) Math.ceil((double) DateTimeUtil.MINUTES_PER_HOUR / intervals);
        double QUANTITY_PER_LOAD = 5;
        double hourlyRequirement = numberOfTruck * QUANTITY_PER_LOAD;

        return Math.min(hourlyRequirement, orderQuantity);
    }
}
