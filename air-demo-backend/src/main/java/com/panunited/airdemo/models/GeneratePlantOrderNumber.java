package com.panunited.airdemo.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "GENERATE_PLANT_ORDER_NUMBER")
public class GeneratePlantOrderNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long plantId;
    private int orderNo;
    private LocalDate  createdDate;

    public static GeneratePlantOrderNumber init(Long plantId) {
        GeneratePlantOrderNumber generateDeliveryOrderNumber = new GeneratePlantOrderNumber();
        generateDeliveryOrderNumber.setPlantId(plantId);
        generateDeliveryOrderNumber.setCreatedDate(LocalDate.now());
        return generateDeliveryOrderNumber;
    }
}

