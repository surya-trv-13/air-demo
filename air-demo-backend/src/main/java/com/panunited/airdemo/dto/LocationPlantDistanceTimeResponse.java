package com.panunited.airdemo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationPlantDistanceTimeResponse {

    private Long plantId;
    private String plantCode;
    private Double distance;
    private Double travellingTime;

}
