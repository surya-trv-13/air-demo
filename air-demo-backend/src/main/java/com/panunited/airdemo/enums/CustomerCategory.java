package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum CustomerCategory {
    TRADING("Trading"),
    NON_TRADING("Non Trading");

    private final String name;

    CustomerCategory(String name) {
        this.name = name;
    }

    public static CustomerCategory getByName(String name) {
        for (CustomerCategory c : CustomerCategory.values()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

}