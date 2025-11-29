package com.panunited.airdemo.enums;

import lombok.Getter;

@Getter
public enum AppsType {

    BOOKING_APP("Booking App"),
    ECOMAX_APP("Ecomax App"),
    DRIVER_APP("Driver App"),
    PORTAL("Portal")
    ;

    private String module;

    AppsType(String module) {
        this.module = module;
    }

}
