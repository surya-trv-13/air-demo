package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.panunited.airdemo.enums.StatusCode;
import com.panunited.airdemo.models.Customer;
import com.panunited.airdemo.models.Product;
import com.panunited.airdemo.models.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderSaveResponse {

    private Boolean isSuccess;
    private Long id;
    private String orderNo;
    private StatusCode status;
    private String pendingReason;
    private List<String> warningMessages;
    @JsonIgnore
    private Customer customer;
    @JsonIgnore
    private Project project;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private Product product;
    @JsonIgnore
    private Double orderQuantity;
    @JsonIgnore
    private String cancelReason;
    @JsonIgnore
    private String rejectReason;
    @JsonIgnore
    private LocalDate orderDate;
    @JsonIgnore
    private LocalTime startTime;

    public OrderSaveResponse() {
        isSuccess = false;
    }
}
