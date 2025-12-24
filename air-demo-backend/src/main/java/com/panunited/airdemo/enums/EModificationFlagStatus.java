package com.panunited.airdemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EModificationFlagStatus {

    CANCELLATION("Cancellation", true),
    ERROR_BY_COMMAND_CENTER("Error by Command Center/Dispatcher", true),
    ERROR_BY_CUSTOMER("Error by Customer", true),
    ERROR_BY_DRIVER("Error by Driver", true),
    ERROR_BY_PLANT("Error by Plant", true),
    ERROR_BY_SALE("Error by Sale", true),
    PARTIAL_REJECTION_BY_CUSTOMER("Partial Rejection by customer", true),
    REJECTED_BY_CUSTOMER("Rejected by Customer", true),
    ERROR_AT_PLANT_ONLY("Error at Plant only", false),
    OTHERS("Others", false);

    private final String description;
    private final boolean active;
}
