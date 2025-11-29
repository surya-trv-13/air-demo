package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum NotificationType {

    EMAIL("Email"),
    SMS("SMS"),
    NONE("None");

    private String name;

    NotificationType(String name) {
        this.name = name;
    }
}

