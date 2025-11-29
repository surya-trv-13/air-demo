package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocationPlantDistanceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PLANT_ID")
    private Plant plant;
    @Column(name="PLANT_ID", insertable = false, updatable = false)
    private Long plantId;
    private Double distance;
    private Double travellingTime;

    public Long getOrderId() { return this.orderId.getId(); }
}

