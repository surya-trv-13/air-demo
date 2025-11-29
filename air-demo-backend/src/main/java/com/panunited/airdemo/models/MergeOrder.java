package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.StatusCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MergeOrder extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean multipleDeliveryOrder;
    private Double totalQuantity;
    @OneToMany(mappedBy = "mergeOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "mergeOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DeliveryOrder> deliveryOrder = new HashSet<>();

    private StatusCode status;
    private LocalDate orderDate;
    private LocalTime startTime;
    private Boolean continuousAssignDeliveryOrder;

    public MergeOrder() {
        this.continuousAssignDeliveryOrder = false;
    }

}
