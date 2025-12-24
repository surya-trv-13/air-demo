package com.panunited.airdemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderDetailID;
    private Long orderHeaderID;
    private String orderNo;
    private Integer loadNo;
    private Double progQty;
    private Double plannedQty;
    private Date startTime;
    private Date actualStartTime;
    private String truckNo;
    private Integer doNo;
    private Double doQty;
    private Date doDate;
    private String freeTexts;
    private String comments;
    private String actualBatchingPlant;
    private String plantCode;
    private Integer version;
    private Integer createdBy;
    private Date createdDate;
    private Integer updatedBy;
    private Date updatedDate;
    private String cubeFamily;
    private String cubeSite;
    private String assignedPlant;
    private Integer assignedBP;
    private String assignedBy;
    private Date assignedTime;
    private Boolean isBuddyDO;
    private String productCode;
    private String productDesc;
    private String HDBSealNo;
    private String qtName;
    private Boolean isLocked;
    private Double sandPercent;
    private Double dustPercent;
    private Double slum;
    private Double returnedQty;
    private Boolean showFlag;
    private Boolean isFirstDO;
    private String batchedProduct;
    private Boolean thermometer;
    private Boolean isSwitchQty;
    private Double replaceProgQty;
    private Boolean wpReject;
    private Integer wpRejectReasonId;
    private Integer pushFwdReasonId;
    private Long orderProductId;
    private Boolean isAutoAssignOrder;
    private Long plantQtId;
    private String batcher;
    private String slumpChecker;
    private String predictedPlant;
    private Integer reassignReasonId;
    private String reassignReason;
    private Integer predictedBp;
    private String cheapestPlant;
    private Date edoSubmittedDate;
    private Date hdbSealNoSubmittedDate;
    private Date eDOCompletedDate;
    private Double acceptedQty;
    private Double slump;
    private Double temperature;
    private Date arrivalTime;
    private Date dischargeStartTime;
    private Date dischargeEndTime;
    private String edoComments;
    private byte[] receivedBySignature;
    private Boolean customerSignOff;
    private String customerSignOffName;
    private String customerSignLaterName;
    private byte[] consultantSignature;
    private Boolean consultantSignOff;
    private String consultantSignOffName;
    private String consultantSignLaterName;
    private Long applicatorId;
    private Long passcodeId;
    private Long driverId;
    private Date doNoSubmittedDate;
    private String eDOStatus;
}
