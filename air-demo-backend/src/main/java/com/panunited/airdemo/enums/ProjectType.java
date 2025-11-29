package com.panunited.airdemo.enums;
import lombok.Getter;

@Getter
public enum ProjectType {
    NON_PROJECT("Non-Project"),
    PROJECT("Project"),
    NON_CONCRETE("NON_CONCRETE");

    private final String name;

    ProjectType(String name) {
        this.name = name;
    }
}
