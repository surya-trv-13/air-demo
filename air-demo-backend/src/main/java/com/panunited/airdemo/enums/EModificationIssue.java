package com.panunited.airdemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EModificationIssue {
    ADMIN_ISSUES("Admin issues", true),
    QUALITY_ISSUES("Quality issues", true),
    MATERIAL_ISSUES("Material issues", true);

    private final String issue;
    private final boolean active;
}

