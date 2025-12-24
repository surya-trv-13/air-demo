package com.panunited.airdemo.enums;

public enum EModificationType {
    ERROR, CANCEL;

    public DOStatus toDOStatus() {
        if (this == ERROR) {
            return DOStatus.EMOD_ERROR;
        } else if (this == CANCEL) {
            return DOStatus.EMOD_CANCEL;
        }
        return null;
    }
}
