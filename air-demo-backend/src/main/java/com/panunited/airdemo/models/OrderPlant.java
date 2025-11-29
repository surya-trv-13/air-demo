package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.PlantType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PlantType plantType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PLANT_ID")
    private Plant plant;
    private Double quantity;
    private LocalDateTime startTime;
    private Integer intervals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;
    private Double cycleCount;



    public Long getPlant() { return Optional.ofNullable(this.plant).map(Plant::getId).orElse(null); }



    public Long getOrder() { return Optional.ofNullable(this.order).map(Order::getId).orElse(null); }

    public boolean isSameData(OrderPlant orderPlant) {
        if (this == orderPlant)
            return true;
        if (orderPlant == null)
            return false;
        return Objects.equals(id, orderPlant.id)
                && Objects.equals(order, orderPlant.order)
                && Objects.equals(plant, orderPlant.plant)
                && plantType == orderPlant.plantType
                && Objects.equals(quantity, orderPlant.quantity)
                && Objects.equals(startTime, orderPlant.startTime)
                && Objects.equals(intervals, orderPlant.intervals)
                && Objects.equals(cycleCount, orderPlant.cycleCount);
    }

}
