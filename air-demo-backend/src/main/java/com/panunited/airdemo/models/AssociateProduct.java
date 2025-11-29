package com.panunited.airdemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class AssociateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long associateProductId;
    private String associateProductCode;
    private String associateProductDescription;
    private Boolean selectedByCustomer;
    private Boolean confirmedByDispatcher;
    private String unitOfMeasurement;
    private String billingUom;
    private Boolean associateProductStatus;
    private String mainAccount;
    private String subAccount;
    private boolean alertDriver;

}
