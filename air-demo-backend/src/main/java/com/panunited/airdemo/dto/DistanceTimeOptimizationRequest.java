package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DistanceTimeOptimizationRequest {

    @JsonProperty("PlantCode")
    private List<String> plantCode;
    @JsonProperty("SiteCoordinate")
    private List<String> siteCoordinate;
    @JsonProperty("Hour (New Zealand Time)")
    private List<Integer> hour;

    public void setHour (Integer currentHour) {
        this.hour = new ArrayList<>();
        if(currentHour != null) {
            this.hour.add(currentHour);
        }
    }
}
