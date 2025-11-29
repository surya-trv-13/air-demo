package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum CylinderTestType {

    STANDARD("Standard Test"), SPECIAL("Special Test"), INHOUSE("InHouse Test"), TESTER("Tester Test"), NONE("NONE");

    private final String name;

    private CylinderTestType(String name) {
        this.name = name;
    }
}
