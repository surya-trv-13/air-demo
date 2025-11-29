package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum PrintPageType {
    ALL("All Pages"),

    FIRST("First Page");

    private String name;

    PrintPageType(String name) {
        this.name = name;
    }
}

