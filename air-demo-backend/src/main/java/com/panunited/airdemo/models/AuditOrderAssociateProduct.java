package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.AuditOrderAssociateProductStatus;
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
public class AuditOrderAssociateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderAssociateProductId;
    private Long orderAssociateProductId;
    private Long associateProductId;
    private Long materialId;
    private Long bundledWith;
    private Double quantity;
    private Long orderId;
    private Long updatedBy;
    private LocalDateTime updatedDate;
    private AuditOrderAssociateProductStatus status;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}

