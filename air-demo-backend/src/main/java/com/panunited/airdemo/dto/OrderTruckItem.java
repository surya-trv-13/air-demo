package com.panunited.airdemo.dto;

import com.panunited.airdemo.models.OrderTruck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderTruckItem {

    private Long orderId;
    private Long truckId;
    private String truckNo;
    private String truckName;
    private Long truckGroupId;
    private String truckGroupName;

    public OrderTruckItem(OrderTruck orderTruck) {
        truckId = orderTruck.getTruck() != null ? orderTruck.getTruck().getId(): null;
        truckGroupId = orderTruck.getTruckGroup() != null ? orderTruck.getTruckGroup().getId() : null;
    }
}
