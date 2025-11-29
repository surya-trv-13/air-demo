package com.panunited.airdemo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
public class SaveBookingRequest extends OrderBooking {

    private String deliveryDate;
    private String deliveryTime;
    private Boolean deliveryAtAnyTime;

    @NotNull(message = "Invalid Save Booking: strengthId is null")
    private Long strengthId;

    private Long plantId;
    private String deviceType;
    private String pendingReason;

    private Double hourlyRequirement;
    private Long contactUserId;

    private Boolean isIgnoreDeliveryDateTimeChecking;

}
