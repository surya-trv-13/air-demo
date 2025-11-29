package com.panunited.airdemo.models;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class TechnicianTimeOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="TECHNICIAN_ID")
    private Technician technician;

    @Column(name="TECHNICIAN_ID", insertable = false, updatable = false)
    private Long technicianId;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fromDatetime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime toDatetime;
    private Long createdBy;
    private LocalDateTime createdDate;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        this.createdDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}
