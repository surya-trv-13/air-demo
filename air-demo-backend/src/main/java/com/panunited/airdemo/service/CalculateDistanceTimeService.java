package com.panunited.airdemo.service;


import com.panunited.airdemo.dto.DistanceTimeOptimizationRequest;
import com.panunited.airdemo.dto.DistanceTimeOptimizationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalculateDistanceTimeService {

    public List<DistanceTimeOptimizationResponse> requestDistanceTimeOptimization(DistanceTimeOptimizationRequest requestObject) {
        try {
            List<DistanceTimeOptimizationResponse> distanceTimeOptimizationResponses = getDistanceTimeOptimizationResponses();
            log.info("Response: " + distanceTimeOptimizationResponses);
            return distanceTimeOptimizationResponses;
        } catch (Exception e) {
            log.error("Exception Occured while getting the Distance Time", e);
        }
        return null;
    }

    private static List<DistanceTimeOptimizationResponse> getDistanceTimeOptimizationResponses() {
        List<DistanceTimeOptimizationResponse> distanceTimeOptimizationResponses = new ArrayList<>();
        DistanceTimeOptimizationResponse distanceTimeOptimizationResponse = new DistanceTimeOptimizationResponse();
        distanceTimeOptimizationResponse.setDistance(11000L);
        distanceTimeOptimizationResponse.setTravellingTime(30L);
        distanceTimeOptimizationResponse.setPlantCode("21");
        distanceTimeOptimizationResponses.add(distanceTimeOptimizationResponse);

        DistanceTimeOptimizationResponse distanceTimeOptimizationResponse2 = new DistanceTimeOptimizationResponse();
        distanceTimeOptimizationResponse2.setDistance(14000L);
        distanceTimeOptimizationResponse2.setTravellingTime(20L);
        distanceTimeOptimizationResponse2.setPlantCode("22");

        distanceTimeOptimizationResponses.add(distanceTimeOptimizationResponse2);
        return distanceTimeOptimizationResponses;
    }
}
