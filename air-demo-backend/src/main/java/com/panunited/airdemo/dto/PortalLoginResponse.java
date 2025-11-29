package com.panunited.airdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PortalLoginResponse {

    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
}
