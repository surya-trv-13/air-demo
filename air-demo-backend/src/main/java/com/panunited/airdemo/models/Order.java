package com.panunited.airdemo.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.panunited.airdemo.enums.NotificationFrequency;
import com.panunited.airdemo.enums.NotificationType;
import com.panunited.airdemo.enums.PlantType;
import com.panunited.airdemo.enums.StatusCode;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "ORDERS")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;

    @Enumerated(EnumType.STRING)
    private StatusCode status;

    @Column(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @Column(name = "PARENT_ORDER_ID", insertable = false, updatable = false)
    private Long parentOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_ORDER_ID")
    private Order parentOrder;

    @Column(name = "PROJECT_ID", insertable = false, updatable = false)
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PROJECT_ID")
    private Project project;

    private Double latitude;
    private Double longitude;
    private String address;

    @Column(name = "LOCATION_ID", insertable = false, updatable = false)
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LOCATION_ID")
    private Location location;

    @Column(name = "PRODUCT_ID", insertable = false, updatable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name="PURCHASE_ORDER_ID", insertable = false, updatable = false)
    private Long purchaseOrderId;

    @Column(name="STRENGTH_ID", insertable = false, updatable = false)
    private Long strengthId;

    private Integer slump;

    @Column(name="DISCHARGE_METHOD_ID", insertable = false, updatable = false)
    private Long dischargeMethodId;
    @Column(name="alert_edo_id")
    private Integer alertEdoID;

    @Column(name="PUMP_ID")
    private Long pumpId;
    private Double pumpDistance;
    @Column(name="STONE_ID")
    private Long stoneId;

    private Double orderQuantity;
    private Double hourlyRequirement;
    private Integer intervals;
    private Double quantityPerLoad;

    private LocalDate orderDate;
    private LocalTime startTime;
    private Boolean deliveryAtAnyTime;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {})
    private Set<OrderAssociateProduct> associateProducts = new HashSet<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {})
    private Set<OrderPlant> assignedPlants = new HashSet<>();


    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private OrderCylinderTest cylinderTest;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {})
    private Set<OrderTruck> trucks = new HashSet<>();

    private Boolean technicianOnSite;

    @Column(name = "CONTACT_USER_ID", insertable = false, updatable = false)
    private Long contactUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="CONTACT_USER_ID")
    private CustomerContact customerContact;

    @Enumerated(EnumType.STRING)
    private NotificationType notification;
    private String specialAccess;
    private String remarks;

    @Column(name="STRUCTURE_ID", insertable = false, updatable = false)
    private  Long structureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="STRUCTURE_ID")
    private Structure structure;


    private String structureRemarks;
    @Enumerated(EnumType.STRING)
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
    private String errorSyncNote;

    private LocalDateTime orderDateTimeUtc;
    private String missingInfoResult;

    @Column(name="is_called_do")
    private boolean calledDo;

    public Order() {
        progressiveQuantity = 0.0;
        stretchProfile = false;
        continuousAssignDeliveryOrder = false;
    }

    public void setOrderQuantity(Double orderQuantity) {
        this.orderQuantity = orderQuantity;
        if (this.orderQuantity == null) {
            return;
        }
        if (this.orderQuantity < 6) {
            this.quantityPerLoad = this.orderQuantity;
        } else {
            this.quantityPerLoad = 6.0;
        }
    }

    public void setCustomerId(Long customerId) {
        if (customerId != null) {
            this.customerId = customerId;
        }
    }

    public Long getCustomer() {
        return this.customerId;
    }

    public void setProjectId(Long projectId) {
        if (projectId != null) {
            this.projectId = projectId;
        }
    }

    @Transient
    public Long getMainPlantId() {
        if (assignedPlants != null && !assignedPlants.isEmpty()) {
            return assignedPlants.stream()
                    .filter(assignedPlant -> assignedPlant.getPlantType() == PlantType.MAIN_PLANT)
                    .findFirst()
                    .map(OrderPlant::getPlant)
                    .orElse(null);
        }
        return null;
    }
}