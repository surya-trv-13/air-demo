package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Data
@Table(name="EDO_SUBMISSION_HISTORY")
public class EDOSubmissionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long driverId;
    private Long orderDetailId;
    private String submissionMethod;
    private Date createdDate;
}
