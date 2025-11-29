package com.panunited.airdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderPurchaseOrderItem {
    private Long id;
    private String purchaseOrderNumber;
}