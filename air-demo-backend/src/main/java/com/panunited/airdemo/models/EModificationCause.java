package com.panunited.airdemo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panunited.airdemo.enums.EModificationDisplayMode;
import com.panunited.airdemo.enums.EModificationFlagStatus;
import com.panunited.airdemo.enums.EModificationIssue;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "EMODIFICATION_CAUSE")
public class EModificationCause {
    @Id
    private Long id;
    private String description;
    private Boolean active;
    @Column(name="EMODIFICATION_FLAG_STATUS")
    private EModificationFlagStatus eModificationFlagStatus;
    @Column(name = "EMODIFICATION_ISSUE")
    private EModificationIssue eModificationIssue;
    @Column(name = "EMODIFICATION_DISPLAY_MODE")
    private EModificationDisplayMode eModificationDisplayMode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdDate;
    private Long createdBy;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        this.createdDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}
