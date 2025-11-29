package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum PaymentTermType {
    NET("NET"),
    COD("COD");

    private String name;

    PaymentTermType(String name) {
        this.name = name;
    }
}
