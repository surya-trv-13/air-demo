package com.panunited.airdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderContactItem {

    private Long id;
    private String customerName;
    private String contactNumber;
    private String emailAddress;
}

