package com.panunited.airdemo.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoPlanTable {
    private Integer deliveryOrderNo;
    private Double progress;
    private Double deliveryOrderQuantity;
    private Date startTime;
    private Long plantId;
    private Long batchingPlantId;
    private Integer delay;
    private Long regionId;
    private Double costChange;
    private Double distanceChange;
    private Boolean isManualChanged = false;
    private Integer batchingDuration;
    private Integer currentDeliveryOrderInterval;
    private Integer optimizedBy;
    private Integer costBalanceThreshold;
    private Integer distanceBalanceThreshold;
    private Date onSiteTime;
    private String orderNo;
    private Boolean isFirstHour;
    private String productCode;
    private Integer priority;
    private String ticketNo;
    private String defaultBatchingPlantCode;

    @JsonIgnore
    public String getBpFull() {
        return (getPlantId()==null ? "" : getPlantId())
                + "-"
                + (getBatchingPlantId()==null ? "" : getBatchingPlantId().toString());
    }
    public Boolean getIsManualChanged() {
        return isManualChanged != null && isManualChanged;
    }
}

