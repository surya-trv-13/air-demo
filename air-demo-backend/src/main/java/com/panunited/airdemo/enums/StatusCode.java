package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    ACCEPTED("ACCEPTED", 1),
    PENDING("PENDING", 2),
    REJECTED("REJECTED", 3),
    CANCELLED("CANCELLED", 4),
    DELIVERED("DELIVERED", 5),
    COMPLETED("COMPLETED", 6),
    CONFIRMED("CONFIRMED", 7),
    HOLD("HOLD", 8),
    APPROVED("APPROVED", 14),
    REJECT("REJECT", 15),
    OPEN("OPEN", 16),
    MISSING_INFO("MISSING INFO", 17),
    REVIEW("REVIEW", 18);

    private final String name;
    private final Integer statusValue;

    StatusCode(String name, Integer statusValue) {
        this.name = name;
        this.statusValue = statusValue;
    }
}
