package com.panunited.airdemo.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderRequestDTO {
    private String loginId;
    private String deviceId;
    private Long orderDetailId;
    private Double acceptedQty;
    private Double slump;
    private Double temperature;
    private String dischargeStartTime;
    private String dischargeEndTime;
    private String edoComments;
    private Boolean customerSignOff;
    private String customerSignOffName;
    private String customerSignLaterName;
    private String customerSignature;
    private Boolean consultantSignOff;
    private String consultantSignOffName;
    private String consultantSignLaterName;
    private String consultantSignature;
    private Long wpWorkerId;
    private String passcodeHash;
    private Long cubeDetailId;
    private String arrivalTime;
    private String edoPasscodeSubmittedTime;
    private String wpSubmittedTime;
    private String edoCompleteTime;
    private String edoSubmittedTime;
    private String hdbSealNoSubmittedTime;
    private String doNoSubmittedTime;
    private Boolean isTruckPlanConfirmed;
    private String safetyAcknowledgementTime;
    private Integer doNo;

    public DeliveryOrderRequestDTO() {

    }

    public boolean isValidRequest() {
        return getLoginId() != null && getDeviceId() != null;
    }

    public boolean isValidSubmission() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getAcceptedQty() != null;
    }

    public boolean isValidSubmission_arrivalTime() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getArrivalTime() != null;
    }

    public boolean isValidSubmission_hdbSealNo() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getHdbSealNoSubmittedTime() != null;
    }

    public boolean isValidSubmission_edoPasscode() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getPasscodeHash() != null
                && getEdoPasscodeSubmittedTime() != null;
    }

    public boolean isValidSubmission_wpPasscode() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getWpWorkerId() != null
                && getWpSubmittedTime() != null;
    }

    public boolean isValidSubmission_dischargeStart() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getDischargeStartTime() != null;
    }

    public boolean isValidSubmission_dischargeComplete() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getDischargeEndTime() != null;
    }

    public boolean isValidSubmission_completeEdo() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getEdoCompleteTime() != null;
    }

    public boolean isValidSubmission_doNo() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null
                && getDoNoSubmittedTime() != null;
    }

    public boolean isValidSubmission_confirmedDO() {
        return getLoginId() != null
                && getDeviceId() != null
                && getOrderDetailId() != null;
    }

    public Double getAcceptedQty() {
        return acceptedQty == null || acceptedQty < 0.0d ? null : acceptedQty;
    }

    public Double getSlump() {
        return slump == null || slump < 0.0d ? null : slump;
    }

    public Double getTemperature() {
        return temperature == null || temperature < 0.0d ? null : temperature;
    }

    public String getArrivalTime() {
        return arrivalTime == null || arrivalTime.isEmpty() ? null : arrivalTime;
    }

    public String getDischargeStartTime() {
        return dischargeStartTime == null || dischargeStartTime.isEmpty() ? null : dischargeStartTime;
    }

    public String getDischargeEndTime() {
        return dischargeEndTime == null || dischargeEndTime.isEmpty() ? null : dischargeEndTime;
    }

    public String getEdoComments() {
        return edoComments == null ? "" : edoComments;
    }



    public String getCustomerSignature() {
        return customerSignature == null || customerSignature.isEmpty() ? null : customerSignature;
    }


    public String getConsultantSignature() {
        return consultantSignature == null || consultantSignature.isEmpty() ? null : consultantSignature;
    }

    public Long getWpWorkerId() {
        return wpWorkerId == null ? -1 : wpWorkerId;
    }

}
