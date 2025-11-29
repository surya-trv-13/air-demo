package com.panunited.airdemo.models;

import java.time.LocalDateTime;
import java.util.Set;

import com.panunited.airdemo.enums.CylinderTestType;
import com.panunited.airdemo.enums.DOStatus;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class DeliveryOrder extends BaseModel implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERGE_ORDER_ID")
    private MergeOrder mergeOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;

    private String doNumber;
    private LocalDateTime doDate;
    private Double doQuantity;
    private Integer loadNumber;
    private Double progressiveQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNED_BP")
    private BatchingPlant assignedBP;
    private Long assignedBy;
    private LocalDateTime loadStartTime;
    private LocalDateTime assignedTime;
    private LocalDateTime batchingTime;
    private LocalDateTime dischargeStartTime;
    private LocalDateTime dischargeEndTime;
    private LocalDateTime arrivalTime;
    @Column(name="EDO_SUBMITTED_DATE")
    private LocalDateTime edoSubmittedDate;
    private Double acceptedQuantity;
    private Double slump;
    private Double gwp;
    private Double temperature;
    @Column(name="EDO_COMMENTS")
    private String edoComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TRUCK_ID")
    private Truck truck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DRIVER_ID")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSIGNED_PLANT_ID")
    private Plant assignedPlant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TECHNICIAN_ID")
    private Technician technician;
    private Double technicianOnSiteDuration;

    private DOStatus status;

    @Enumerated(EnumType.STRING)
    private CylinderTestType cylinderTestRequestType = CylinderTestType.NONE;
    private Integer totalCylinderCount;

    @Transient
    Set<OrderDetailSignature> signatures = null;

    @Transient
    private String truckNo;
    private String gwpInfo;
    private Boolean washoutFee;
    private Boolean returnConcrete;
    private Boolean collectedPaperTicket;
    private String preprintDoNumber;
    private String batchingNo;
    private String mixDesignCode;
    private Integer mixDesignVersion;
    private Boolean isSkipCommand;
    private Boolean isBatchVariationReportGenerated;

    private Long batchedBy;

    public DeliveryOrder() {
        this.status = DOStatus.ASSIGNED;
        this.progressiveQuantity = 0.0;
        this.washoutFee = false;
        this.returnConcrete = false;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
        this.assignedTime = DateTimeUtil.getCurrentLocalDateTime();
    }

    public Long getAssignedPlantId() {
        if (assignedPlant == null) {
            return null;
        }
        return assignedPlant.getId();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
