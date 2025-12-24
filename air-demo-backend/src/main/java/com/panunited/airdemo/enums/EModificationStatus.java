package com.panunited.airdemo.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EModificationStatus {
    SUBMITTING("Submitting"),
    PENDING_CCM("Pending for Dispatcher Manager"),
    APPROVED_CCM("Approved by Dispatcher Manager"),
    REJECTED_CCM("Rejected by Dispatcher Manager"),
    FORWARD("Forward"),
    APPROVED_FINANCE("Approved by Finance"),
    REJECTED_FINANCE("Rejected by Finance"),
    WITHDRAW("Withdraw"),
    PENDING_PH("Pending_PlantHead");

    private final String description;
}