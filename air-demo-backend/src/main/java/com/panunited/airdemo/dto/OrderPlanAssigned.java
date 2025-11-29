package com.panunited.airdemo.dto;


import org.mapstruct.Mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Mapper
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlanAssigned {
    private String orderNo;
    private Long orderId;
    private String customerName;
    private String locationName;
    private String projectName;
    private String plantName;
    private String plantCode;
    private Long plantId;
    private Double orderQuantity;
    private Double progressiveQuantity;
    private Double deliveryQuantity;
    private Long batchingPlantId;
    private Integer loadNumber;
    private Long regionId;
    private String regionCode;
    private String message;
    private String productCode;
    private String productDescription;
    private Date startTime;
    private String details;
    private Integer intervals;

    public String orderNoRegion() {
        return this.orderId + "_" + this.orderNo + "_" + regionId;
    }
}
