package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum CustomerType {
    STAFF("Staff"),
    CREDIT("Credit"),
    CASH_SALES("Cash Sales");

    private final String name;

    CustomerType(String name) {
        this.name = name;
    }
}
