package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.CylinderTestType;
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
public class AuditOrderCylinderTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderCylinderTestId;
    private Long orderCylinderTestId;
    private CylinderTestType testType;
    private Integer standardTestNumber;
    Long orderId;
    private Long updatedBy;
    private LocalDateTime updatedDate;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}
