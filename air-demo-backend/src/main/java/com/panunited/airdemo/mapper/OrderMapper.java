package com.panunited.airdemo.mapper;

import com.panunited.airdemo.dto.OrderPlantItem;
import com.panunited.airdemo.dto.OrderSaveRequest;
import com.panunited.airdemo.dto.OrderSaveResponse;
import com.panunited.airdemo.dto.SaveBookingRequest;
import com.panunited.airdemo.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper {
    @Mapping(target = "customer", ignore = true)
    OrderSaveResponse fromOrderToOrderSaveResponse(Order order);

    @Mapping(target = "assignedPlants", source = "assignedPlants")
    Order fromOrderSaveRequestToOrder(OrderSaveRequest orderSaveRequest);

    @Mapping(source = "productId", target = "productId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(source = "deliveryDate", target = "orderDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "deliveryTime", target = "startTime", dateFormat = "H:mm:ss")
    @Mapping(target = "assignedPlants", ignore = true)
    @Mapping(target = "assignedPlants[].plant", ignore = true)
    Order fromSaveBookingRequestToOrder(SaveBookingRequest request);

    @Mapping(source = "id", target = "orderAssociateProductId")
    @Mapping(source = "order", target = "orderId")
    AuditOrderAssociateProduct toAuditOrderAssociateProduct(OrderAssociateProduct orderAssociateProduct);
    List<AuditOrderAssociateProduct> toAuditOrderAssociateProduct(List<OrderAssociateProduct> orderAssociateProducts);

    List<AuditOrderTruck> toAuditOrderTruck(List<OrderTruck> orderTrucks);

    @Mapping(source = "id", target = "orderId")
    AuditOrder toAuditOrder(Order order);

    @Mapping(source = "id", target = "orderPlantId")
    @Mapping(source = "order", target = "orderId")
    @Mapping(source = "plant", target = "plantId")
    AuditOrderPlant toAuditOrderPlant(OrderPlant orderPlant);

    List<AuditOrderPlant> toAuditOrderPlant(List<OrderPlant> orderPlants);

    @Mapping(source = "id", target = "orderCylinderTestId")
    @Mapping(source = "order", target = "orderId")
    AuditOrderCylinderTest toAuditOrderCylinderTest(OrderCylinderTest orderCylinderTest);

    @Mapping(source = "id", target = "orderCylinderTestAgeId")
    AuditOrderCylinderTestAge toAuditOrderCylinderTestAge(OrderCylinderTestAge orderCylinderTestAge);

    Set<AuditOrderCylinderTestAge> toAuditOrderCylinderTestAge(Set<OrderCylinderTestAge> orderCylinderTestAges);

    @Mapping(target = "plant.id", source = "plantId")
    OrderPlant map(OrderPlantItem request);
}
