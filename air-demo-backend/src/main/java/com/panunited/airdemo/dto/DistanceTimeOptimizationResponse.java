package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DistanceTimeOptimizationResponse {

    @JsonProperty("Distance")
    private Long distance;
    @JsonProperty("DistanceSource")
    private String distanceSource;
    @JsonProperty("ErrorMessage")
    private String errorMessage;
    @JsonProperty("PlantCode")
    private String plantCode;
    @JsonProperty("SiteCoordinate")
    private String siteCoordinate;
    @JsonProperty("TravelingTime")
    private Long travellingTime;
    @JsonProperty("TravelingTimeSource")
    private String travelingTimeSource;
}
