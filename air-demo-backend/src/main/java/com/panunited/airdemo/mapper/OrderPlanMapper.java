package com.panunited.airdemo.mapper;

import com.panunited.airdemo.dto.OrderPlanAssigned;
import com.panunited.airdemo.dto.OrderPlanResponse;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface OrderPlanMapper {
    @Mapping(target = "doPlans", ignore = true)
    public abstract OrderPlanResponse toOrderPlanResponse(OrderPlanAssigned dto, @Context List<OrderPlanAssigned> doPlans);

    @Mapping(target = "doPlans", ignore = true)
    public abstract OrderPlanResponse toOrderPlanResponse(OrderPlanAssigned dto);
}
