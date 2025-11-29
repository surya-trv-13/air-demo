package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String truckNo;
    private String truckName;
    private String remarks;
    private boolean active;
    private String registrationNo;
    @Transient
    private Double capacity;
    private Long truckTypeId;
    private Long subcontractorId;

    @Transient
    private Long assignedPlantId;
    private Long mainPlantId;
    private Long supportPlantId;

    @Column(name="IS_SEND_VERIFY_FLAG")
    private boolean sendVerifyFlag;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Transient
    private String truckType;

    @Column(name="is_deleted")
    private boolean deleted;

    @EqualsAndHashCode.Exclude
    private Long createdBy;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdDate;
    @EqualsAndHashCode.Exclude
    private Long updatedBy;
    @EqualsAndHashCode.Exclude
    private LocalDateTime updatedDate;
    @Column(name="IS_DUMMY_TRUCK")
    private boolean dummyTruck;
    @Column(name="IS_DISABLED_DRIVER_APP")
    private boolean disabledDriverApp;
}
