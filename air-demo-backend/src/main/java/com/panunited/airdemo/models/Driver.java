package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private LocalDate dateOfBirth;
    private String phoneNo;
    private Long subconOwner;
    private String email;
    private Long truckId;
    private Long altTruckId;
    private String registrationNo;

    private String licenseClass;
    private LocalDate licenseExpiryDate;
    private Long mainPlantId;
    private String driverGroup;
    private String remarks;
    private Integer workStatus;
    private String workStatusRemarks;

    @Column(name="IS_ACTIVE")
    private boolean active;

    @Column(name="is_deleted")
    private boolean deleted;
}
