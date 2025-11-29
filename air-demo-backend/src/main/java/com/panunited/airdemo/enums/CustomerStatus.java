package com.panunited.airdemo.enums;


import lombok.Getter;

@Getter
public enum CustomerStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String name;

    CustomerStatus(String name) {
        this.name = name;
    }
}
