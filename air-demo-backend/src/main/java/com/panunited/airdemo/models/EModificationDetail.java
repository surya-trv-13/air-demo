package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.EModificationFlagStatus;
import com.panunited.airdemo.enums.EModificationStatus;
import com.panunited.airdemo.enums.EModificationType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "EMODIFICATION_DETAIL")
public class EModificationDetail extends BaseModel {
    @Id
    private Long id;
    private EModificationType type;
    private EModificationStatus status;

    @Column(name = "DELIVERY_ORDER_ID", insertable = false, updatable = false)
    private Long deliveryOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ORDER_ID")
    private DeliveryOrder deliveryOrder;

    @Column(name = "PLANT_CODE", insertable = false, updatable = false)
    private Long plantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANT_CODE")
    private Plant plant;

    private EModificationFlagStatus flagStatus;
    @Column(name = "EMODIFICATION_CAUSE_ID", insertable = false, updatable = false)
    private Long eModificationCauseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMODIFICATION_CAUSE_ID")
    private EModificationCause eModificationCause;

    @Column(name="EMODIFICATION_ISSUE")
    private String eModificationIssue;
    private String modification;
    private Double wastage;
    private Double divertedQuantity;
    private String newDoNumber;
    private Double newQuantity;

    @Column(name = "NEW_PRODUCT_ID", insertable = false, updatable = false)
    private Long newProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEW_PRODUCT_ID")
    private Product product;

    @Column(name = "NEW_LOCATION_ID", insertable = false, updatable = false)
    private Long newLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEW_LOCATION_ID")
    private Location location;

    private Double newLatitude;
    private Double newLongitude;
    private String newAddress;
    private String newTruckNumber;
    private Boolean driverClaim;
}