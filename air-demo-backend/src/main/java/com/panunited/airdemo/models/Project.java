package com.panunited.airdemo.models;

import java.math.BigDecimal;
import java.time.LocalDate;


import com.panunited.airdemo.enums.PrintPageType;
import com.panunited.airdemo.enums.ProjectType;
import com.panunited.airdemo.utils.CommonUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String projectName;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;
    private Boolean active = true;
    private String projectMailingAddress1;
    private String projectMailingAddress2;
    private String city;
    private String state;
    private String posCode;

    @Column(name="PROJECT_CODE")
    private String projectCode;

    private String projectCountry = CommonUtils.DEFAULT_COUNTRY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REGION_ID")
    private Region region;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PLANT_ID")
    private Plant plant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SALESPERSON_ID")
    private Salesperson salesperson;

    private LocalDate projectStartDate;
    private LocalDate projectEndDate;

    private BigDecimal estimatedProjectQuantity;
    private BigDecimal suppliedQuantity;
    private BigDecimal balanceQuantity;

    private Integer printCopyCount = 1; // No. of copies

    @Enumerated(EnumType.STRING)
    private PrintPageType printPageType; // All or First

    private String email; // Allow Multiple

    private Long customerId;
    private Boolean requiredPurchaseOrderNo;
    private Boolean deliveryOrderRequired;
    private Boolean defaultProject;

    @Column(name="MARGIN_PRICE")
    private BigDecimal projectMargin;

}
