package com.panunited.airdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDetailListResponse extends OrderListResponse {
    private List<OrderTruckItem> fleetGroupTrucks;
}
