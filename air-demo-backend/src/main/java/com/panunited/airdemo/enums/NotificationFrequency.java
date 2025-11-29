package com.panunited.airdemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationFrequency {
    EVERY_LOAD("Every Load",1),
    EVERY_2_LOADS("Every 2 Loads",2),
    EVERY_3_LOADS("Every 3 Loads",3),
    EVERY_5_LOADS("Every 5 Loads",5),
    EVERY_10_LOADS("Every 10 Loads",10),
    EVERY_15_LOADS("Every 15 Loads",15),
    EVERY_20_LOADS("Every 20 Loads",20);

    private final String description;
    private final int loads;
}
