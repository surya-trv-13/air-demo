package com.panunited.airdemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Plant extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String plantCode;
    @NotBlank
    private String plantName;
    @NotBlank
    private String plantAddress;
    @NotBlank
    private Boolean isActive;
    @NotBlank
    private String plantTelephone;
    @NotBlank
    private Double plantLongitude;
    @NotBlank
    private Double plantLatitude;
    @NotBlank
    private Long geoRadius;
    private String queueGeoBoundary;
    private String deliveryGeoBoundary;

    @NotBlank
    private Integer numberOfBp;
    @NotBlank
    private Integer allowNumTruck;
    @NotBlank
    @Column(name="waiting_time_per_bp_in_second")
    private Integer waitingTimePerBPInSecond;
    @NotBlank
    private String fleetOptimisationGroup;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="REGION_ID")
    private Region region;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="SELF_BILLED_CUSTOMER_ID")
    private Customer selfBilledCustomer;

    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalDate configDate;

    private Integer batchFee;
    private Integer smallLeadTime;
    private Integer bigLeadTime;
    private Integer baselineBatchTime;

    @Column(name="is_deleted")
    private boolean deleted;

    @Column(name="is_auto_join_queue")
    private boolean autoJoinQueue;

    @Column(name="is_closed")
    private boolean closed;

    public String getPlantCode() {
        if (plantCode != null) {
            return plantCode.trim();
        } else {
            return null;
        }
    }
}