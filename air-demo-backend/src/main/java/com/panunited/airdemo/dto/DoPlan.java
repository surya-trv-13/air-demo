package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panunited.airdemo.models.Plant;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoPlan {
    private Integer deliveryOrderNo;
    private Integer loadNumber;
    private Double progressiveQuantity;
    private Double deliveryQuantity;
    private Date startTime;
    private String orgPlantShortName;
    private String newPlantShortName;
    private String plantCode;
    private Long plantId;
    private String message;
    private Long batchingPlantId;
    private Integer delay;
    private Boolean isManualChanged = false;
    private Boolean isAssigned = false;
    private Long regionId;
    public DoPlan(DoPlanTable doPlantTable, String orgPlantCode, Map<String, String> plantCodeToShortName) {
        this.deliveryOrderNo = doPlantTable.getDeliveryOrderNo();
        this.progressiveQuantity = doPlantTable.getProgress();
        this.deliveryQuantity = doPlantTable.getDeliveryOrderQuantity();
        this.delay = doPlantTable.getDelay();
        this.startTime = doPlantTable.getStartTime();
        this.orgPlantShortName = plantCodeToShortName.get(orgPlantCode);
        this.newPlantShortName = plantCodeToShortName.get(doPlantTable.getPlantId());
        this.batchingPlantId = doPlantTable.getBatchingPlantId();
        this.isManualChanged = doPlantTable.getIsManualChanged();
        this.plantId = doPlantTable.getPlantId();
    }
    public static DoPlan DoPlanBuild(DoPlanTable doPlantTable, Long regionId) {
        return new DoPlan(doPlantTable, regionId, null, null);
    }
    public DoPlan(DoPlanTable doPlantTable, Long regionId, Long orgPlantId, Map<Long, Plant> plantMap) {
        Plant plant = plantMap.get(doPlantTable.getPlantId());
        this.deliveryOrderNo = doPlantTable.getDeliveryOrderNo();
        this.progressiveQuantity = doPlantTable.getProgress();
        this.deliveryQuantity = doPlantTable.getDeliveryOrderQuantity();
        this.delay = doPlantTable.getDelay();
        this.startTime = doPlantTable.getStartTime();
        this.orgPlantShortName = plantMap.get(orgPlantId).getPlantName();
        this.newPlantShortName = plant.getPlantName();
        this.batchingPlantId = doPlantTable.getBatchingPlantId();
        this.plantCode = plant.getPlantCode();
        this.isManualChanged = doPlantTable.getIsManualChanged();
        this.regionId = regionId;
        this.plantId = doPlantTable.getPlantId();
    }

    @JsonIgnore
    public String getPlantBpFull() {
        return (newPlantShortName==null ? "" : newPlantShortName)
                + "-"
                + (batchingPlantId==null ? "" : batchingPlantId.toString());
    }
    public Boolean getIsManualChanged() {
        return isManualChanged != null && isManualChanged;
    }
    public Boolean getIsAssigned() {
        return isAssigned != null && isAssigned;
    }

}
