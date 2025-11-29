package com.panunited.airdemo.dto;

import java.time.LocalDateTime;

public interface OrderPlantItemProjection {
    Long getId();
    String getPlantType();
    Long getPlantId();
    Double getQuantity();
    LocalDateTime getStartTime();
    Integer getIntervals();
    Long getOrderId();
}
