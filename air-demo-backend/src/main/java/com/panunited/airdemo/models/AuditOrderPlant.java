package com.panunited.airdemo.models;


import com.panunited.airdemo.enums.PlantType;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class AuditOrderPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderPlantId;
    private Long orderPlantId;
    private PlantType plantType;
    Long plantId;
    private Double quantity;
    private LocalDateTime startTime;
    private Integer intervals;
    Long orderId;
    private Long updatedBy;
    private LocalDateTime updatedDate;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}
