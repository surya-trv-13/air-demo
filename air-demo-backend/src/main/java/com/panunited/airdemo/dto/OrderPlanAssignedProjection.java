package com.panunited.airdemo.dto;

import java.util.Date;

public interface OrderPlanAssignedProjection {
     String getOrderNo();
     Long getOrderId();
     String getCustomerName();
     String getLocationName();
     String getProjectName();
     String getPlantName();
     String getPlantCode();
     Long getPlantId();
     Double getOrderQuantity();
     Long getRegionId();
     String getRegionCode();
     String getProductCode();
     String getProductDescription();
     Date getStartTime();
     Integer getIntervals();
}
