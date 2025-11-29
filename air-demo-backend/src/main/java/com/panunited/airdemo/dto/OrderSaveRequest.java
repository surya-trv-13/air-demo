package com.panunited.airdemo.dto;

import com.panunited.airdemo.enums.NotificationFrequency;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderSaveRequest extends OrderBooking {

    private LocalDate orderDate;
    private LocalTime startTime;

    private Integer alertEdoID;
    private NotificationFrequency notificationFrequency ;

    private Boolean saveLayerOrder = false;
    private Long orderIdWithinLayers;
    private Integer layerIntervals;

    private List<LocationPlantDistanceTimeResponse> locationPlantDistanceTime;
    private List<Long> fleetTruckIds = new ArrayList<>();
    private List<OrderTruckItem> fleetGroupTrucks;
    private Long regionId;
    private Double quantityPerLoad;
}
