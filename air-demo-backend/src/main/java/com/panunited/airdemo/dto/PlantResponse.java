package com.panunited.airdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantResponse {
    private Long plantId;
    private String plantCode;
    private String plantName;
    private Boolean isActive;
}
