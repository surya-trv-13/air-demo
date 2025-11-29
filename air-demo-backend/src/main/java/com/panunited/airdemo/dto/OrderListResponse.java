package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panunited.airdemo.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class OrderListResponse {
    private Long id;
    private String orderNo;
    private StatusCode status;
    private LocalDate orderDate;
    private Long customerId;
    private String customerName;
    private Long projectId;
    private String projectName;
    private String address;
    private Long productId;
    private String productCode;
    private String productDescription;
    private Double orderQuantity;
    private Double progressiveQuantity;
    private Double actualProgressiveQuantity;
    private Double quantityPerLoad;
    private LocalTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime batchTime;
    private LocalDate createdDate;
    private Long createdBy;
    private Double availableAssignedDOQuantity;
    // private Boolean isEditableOrAssignable;
    private Boolean isEditable;
    private Boolean isAssignable;
    private Integer mergeWithSingleDeliveryOrder;
    private Integer mergeWithMultipleDeliveryOrder;
    private Boolean mergingOrder;
    private Long orderId;
    private Long mergeOrderId;
    private Boolean multipleDeliveryOrder;
    private Boolean continuousAssignDeliveryOrder;
    private Boolean technicianOnSite;
    private Long layerGroup;
    private String associateProductAgg;
    private Long mainPlantId;
    private String mainPlantName;
    private Long regionId;
    private String regionName;
    private String ctOrderCode;
    private Boolean calledDeliveryOrder;
    private Boolean hasInvoicedDo;
    private Boolean hasInvoiceDraftGeneratedDo;
    private String testType;
    private Boolean haveCert;
    private String missingInfoResult;
    private String doNumber;
    private Integer intervals;
    private String customerCode;
    private Double latitude;
    private Double longitude;
}
