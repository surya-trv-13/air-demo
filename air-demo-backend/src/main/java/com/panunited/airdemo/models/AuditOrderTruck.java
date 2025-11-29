package com.panunited.airdemo.models;

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
public class AuditOrderTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditOrderTruckId;
    Long orderId;
    Long truckId;
    Long truckGroupId;
    private Long updatedBy;
    private LocalDateTime updatedDate;

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedDate = DateTimeUtil.getCurrentLocalDateTime();
    }

}
