package com.panunited.airdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContactDetailResponse {
    private Long customerContactId;
    private String serialNumber;
    private String contactName;
    private String contactNumber;
    private String emailAddress;
    private String type;
}

