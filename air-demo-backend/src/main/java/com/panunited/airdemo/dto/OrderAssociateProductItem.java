package com.panunited.airdemo.dto;

import com.panunited.airdemo.models.Order;
import com.panunited.airdemo.models.OrderAssociateProduct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
public class OrderAssociateProductItem {

    private Long associateProductId;
    @NotNull(message = "Invalid Associate Product: materialId is null")
    private Long materialId;
    @NotNull(message = "Invalid Associate Product: quantity is null")
    @Min(value = 0, message = "Invalid Associate Product: quantity must be at least 0")
    private Double quantity;
    private Long bundledWith;
    @Valid
    private List<OrderAssociateProductItem> bundledAssociateProducts;

    public OrderAssociateProduct changeToOrderAssociateProduct(Order order) {
        OrderAssociateProduct orderAssociateProduct = new OrderAssociateProduct();
        orderAssociateProduct.setAssociateProductId(this.associateProductId);
        orderAssociateProduct.setMaterialId(this.materialId);
        orderAssociateProduct.setQuantity(this.quantity);
        orderAssociateProduct.setOrder(order);
        return orderAssociateProduct;
    }

    public OrderAssociateProduct changeToOrderAssociateProduct(Order order, Long bundledWith) {
        OrderAssociateProduct orderAssociateProduct = changeToOrderAssociateProduct(order);
        orderAssociateProduct.setBundledWith(bundledWith);
        return orderAssociateProduct;
    }
}

