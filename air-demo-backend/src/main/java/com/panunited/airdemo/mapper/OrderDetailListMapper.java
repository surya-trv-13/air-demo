package com.panunited.airdemo.mapper;


import com.panunited.airdemo.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailListMapper {
    List<OrderDetailListResponse> fromOrderListResponse(List<OrderListResponse> orderList);
    OrderListResponse fromProjection(OrderListResponseProjection orderListResponseProjection);
    @Mapping(source = "plantId", target = "plantId")
    OrderPlantItem fromOrderPlantProjection(OrderPlantItemProjection orderPlantItemProjection);
}
