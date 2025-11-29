package com.panunited.airdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPlanSearchParams {
    private Long customerId;
    private Long locationId;
    private Long projectId;
    private Long regionId;
}
