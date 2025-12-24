package com.panunited.airdemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EModificationWorkflowAction {
    SUBMITTED_FOR_APPROVAL("Submitted For Approval"),
    APPROVED_FINANCE("Approved by Finance"),
    FORWARD_FINANCE("Forward by Finance"),
    APPROVED_CCM("Approved by Dispatcher Manager"),
    REJECTED_CCM("Rejected by Dispatcher Manager"),
    REJECTED_FINANCE("Rejected by Finance"),
    WITHDRAW("Withdraw");

    private final String description;
}

