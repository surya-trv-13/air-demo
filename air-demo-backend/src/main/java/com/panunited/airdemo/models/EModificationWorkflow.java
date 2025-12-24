package com.panunited.airdemo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panunited.airdemo.enums.EModificationStatus;
import com.panunited.airdemo.enums.EModificationWorkflowAction;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "EMODIFICATION_WORKFLOW")
public class EModificationWorkflow {
    @Id
    private Long id;

    @Column(name = "EMODIFICATION_DETAIL_ID", insertable = false, updatable = false)
    private Long eModificationDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMODIFICATION_DETAIL_ID")
    private EModificationDetail eModificationDetail;

    private Long submissionFrom;
    private Long submissionTo;
    private EModificationStatus submissionFromStatus;
    private EModificationStatus submissionToStatus;
    private EModificationWorkflowAction action;
    private String remarks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdDate;
    @ReadOnlyProperty
    private String submissionFromUser;
    @ReadOnlyProperty
    private String submissionToUser;

    public String getActionDescription() {
        if (action != null) {
            return action.getDescription();
        }
        return null;
    }
}
