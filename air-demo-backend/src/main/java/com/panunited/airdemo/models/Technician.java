package com.panunited.airdemo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panunited.airdemo.enums.TechnicianStatus;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Technician extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long regionId;
    private Long plantId;
    @NotNull
    private LocalTime shiftStartTime;
    @NotNull
    private LocalTime shiftEndTime;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime outTime;
    private TechnicianStatus status;
    private Long customerId;
    private Long projectId;
    private Long locationId;
    @Transient
    private String regionName;
    @Transient
    private String plantCode;
    @Transient
    private String plantName;
    @Transient
    private String customerName;
    @Transient
    private String projectName;
    @Transient
    private String locationName;

    @OneToMany(mappedBy = "technician", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TechnicianTimeOff> timeOffList;

    public TechnicianStatus getActualStatus() {
        if (status == TechnicianStatus.OFFLINE) {
            return status;
        }
        LocalDateTime currentDateTime = DateTimeUtil.getCurrentLocalDateTime();
        for (TechnicianTimeOff timeOff : timeOffList) {
            if (currentDateTime.isAfter(timeOff.getFromDatetime())
                    && currentDateTime.isBefore(timeOff.getToDatetime())) {
                return TechnicianStatus.OFFLINE_TIME_OFF;
            }
        }
        LocalTime currentTimeSystem = LocalTime.now();
        if (!DateTimeUtil.isBetweenTimeRange(currentTimeSystem, shiftStartTime, shiftEndTime)) {
            return TechnicianStatus.OFFLINE_SHIFT_HOURS;
        }
        return status;
    }
}
