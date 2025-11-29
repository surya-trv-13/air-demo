package com.panunited.airdemo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderDto {

    private Long id;

    @NotBlank(message = "Invalid PO Number: PO Number is null")
    private String purchaseOrderNumber;

    @NotBlank(message = "Invalid PO Date: PO Date is null")
    private LocalDate purchaseOrderDate;

    private boolean active;

    private Long projectId;
}