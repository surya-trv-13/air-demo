package com.panunited.airdemo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("Admin"),
    CUSTOMER_ADMIN("Customer Admin"),
    CEO("CEO"),
    PROJECT_TEAM("Project Team"),
    DRIVER("Driver"),
    CUSTOMER("Customer"),
    DISPATCHER("Dispatcher"),
    BATCHER("Batcher"),
    DISPATCHER_MANAGER("Dispatcher Manager"),
    FINANCE_AR("Finance AR"),
    FINANCE_HOD("Finance HOD"),
    LAB("Lab"),
    LAB_MANAGER("Lab Manager"),
    SALES_ADMIN("Sales Admin"),
    SALESPERSON("Salesperson"),
    SALES_MANAGER("Sales Manager"),
    HOUSE_MANAGER("House Manager"),
    MIX_DESIGN_REQUESTER("MIX_DESIGN_REQUESTER"),
    RAW_MATERIAL_REQUESTER("RAW_MATERIAL_REQUESTER"),
    ADVANCE_USER("ADVANCE_USER"),
    HR_CEMENT("HR Cement"),
    SALESPERSON_MD("Salesperson MD"),
    AR_ADMIN("Ar Admin"),
    IT("IT Support");

    private final String description;

    public static Role getRoleByName(String name) {
        for (var r : Role.values()) {
            if (Objects.equals(r.name(), name)) {
                return r;
            }
        }
        return null;
    }
}
