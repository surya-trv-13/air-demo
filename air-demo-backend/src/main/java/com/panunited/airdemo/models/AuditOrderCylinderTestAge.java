package com.panunited.airdemo.models;

import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AuditOrderCylinderTestAge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderCylinderTestAgeId;
    private Long orderCylinderTestAgeId;
    private Long orderCylinderTestId;
    private Integer numberOfCylinder;
    private Integer testAge;
    private String uom;
    private Long updatedBy;
    private LocalDateTime updatedDate;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}