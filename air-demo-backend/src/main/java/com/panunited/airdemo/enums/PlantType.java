package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum PlantType {

    MAIN_PLANT("Main Plant"),
    SUPPORT_PLANT("Support Plant");

    private final String name;

    PlantType(String name) {
        this.name = name;
    }
}
