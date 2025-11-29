package com.panunited.airdemo.dto;

import com.panunited.airdemo.enums.StatusCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Validated
public class OrderBooking {

    private Long id;
    private String orderNo;
    private StatusCode status;
    @NotNull(message = "Invalid Order Booking: customerId is null")
    private Long customerId;
    @NotNull(message = "Invalid Order Booking: projectId is null")
    private Long projectId;
    private Long productId;
    @NotNull(message = "Invalid Order Booking: latitude is null")
    private Double latitude;
    @NotNull(message = "Invalid Order Booking: longitude is null")
    private Double longitude;
    private String address;
    private Long locationId;

    @Min(value = 0, message = "Invalid Order Booking: slump must be at least 0")
    private Integer slump;
    private Long dischargeMethodId;
    private Long pumpId;
    private Double pumpDistance;
    private Long stoneId;

    @Min(value = 0, message = "Invalid Order Booking: orderQuantity must be at least 1")
    private Double orderQuantity;
    @Min(value = 0, message = "Invalid Order Booking: intervals must be at least 0")
    private Integer intervals;

    @Valid
    private List<OrderAssociateProductItem> associateProducts;
    private Boolean technicianOnSite;
    private Double technicianOnSiteDuration;
    private Long structureId;
    private String structureRemarks;

    @Valid
    private OrderCylinderTestItem cylinderTest;
    @Valid
    private Set<OrderPlantItem> assignedPlants = new HashSet<>();
    @Valid
    private OrderContactItem contact;
    private boolean calledDo;
    private OrderPurchaseOrderItem purchaseOrder;

    private String specialAccess;
    private String remarks;
    private String ctDispatchNote;
    private String notification;
    private String cancelReason;
    private String rejectReason;

    private Boolean isBooking;
    private Boolean stretchProfile;

}
