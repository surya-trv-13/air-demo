package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.NotificationFrequency;
import com.panunited.airdemo.enums.NotificationType;
import com.panunited.airdemo.enums.StatusCode;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "AUDIT_ORDERS")
public class AuditOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderId;
    private Long orderId;
    private String orderNo;
    private StatusCode status;
    private LocalDate orderDate;
    private Long customerId;
    private Long projectId;
    private Long productId;
    private Double latitude;
    private Double longitude;
    private String address;
    private Long locationId;
    private Long purchaseOrderId;
    private Long strengthId;
    private Integer slump;
    private Long dischargeMethodId;
    private Long pumpId;
    private Double pumpDistance;
    private Long stoneId;

    private Double orderQuantity;
    private Double hourlyRequirement;
    private Integer intervals;
    private Double quantityPerLoad;


    private LocalTime startTime;
    private Boolean deliveryAtAnyTime;

    private Boolean technicianOnSite;
    private Long contactUserId;
    private NotificationType notification;
    private String specialAccess;
    private String remarks;
    private Long structureId;
    private String structureRemarks;
    @Column(name="alert_edo_id")
    private Integer alertEdoID;
    private NotificationFrequency notificationFrequency;

    private Boolean stretchProfile;
    private Double assignedQuantity;
    private Double progressiveQuantity;
    private Boolean isBooking;
    private String cancelReason;
    private String rejectReason;
    private String holdReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERGE_ORDER_ID")
    private MergeOrder mergeOrder;
    private Boolean continuousAssignDeliveryOrder;

    private Integer layerSequence;
    private Long layerGroup;
    private Integer layerIntervals;

    private String ctOrderCode;
    private Integer ctInterLineNo;
    private Integer ctLoadNo;
    private String ctDispatchNote;

    private LocalDateTime orderDateTimeUtc;
    private String missingInfoResult;
    private Long parentOrderId;

    private Long updatedBy;
    private LocalDateTime updatedDate;
    @Column(name="IS_CALLED_DO")
    private boolean calledDo;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}
