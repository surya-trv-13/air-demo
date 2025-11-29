package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.OrderPlanAssigned;
import com.panunited.airdemo.dto.OrderPlanResponse;
import com.panunited.airdemo.dto.OrderPlanSearchParams;
import com.panunited.airdemo.mapper.OrderPlanMapper;
import com.panunited.airdemo.models.Plant;
import com.panunited.airdemo.repositories.OrderPlanRepository;
import com.panunited.airdemo.repositories.PlantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderPlanService {

    private final OrderPlanMapper mapper;
    private final OrderPlanRepository orderPlanRepository;
    private final PlantRepository plantRepository;

    public List<OrderPlanResponse> getOrderPlans(OrderPlanSearchParams searchParams) {

        Map<Long, Plant> plantMap = plantRepository.findAllList().stream().collect(
                Collectors.toMap(Plant::getId, plant -> plant ));

        // Get assign Plant
        Map<String, List<OrderPlanAssigned>> plantOrderAssignEntityMap =  orderPlanRepository.getOrderPlanAssigns(searchParams.getCustomerId(),
                        searchParams.getProjectId(), searchParams.getRegionId()).stream()
                .collect(Collectors.groupingBy(OrderPlanAssigned::orderNoRegion, Collectors.toList()));
        Map<String, OrderPlanResponse> plantOrderAssignMap = plantOrderAssignEntityMap.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, entry -> mapper.toOrderPlanResponse(entry.getValue().getFirst(), entry.getValue())));
        // Get Order Plan
        List<OrderPlanResponse> plantOrders = orderPlanRepository.getOrderPlans(searchParams.getCustomerId(),
                        searchParams.getProjectId(), searchParams.getRegionId())
                .stream().map(mapper::toOrderPlanResponse).collect(Collectors.toList());
        Set<Long> orderNoSearch = plantOrders.stream().map(OrderPlanResponse::getOrderId).collect(Collectors.toSet());

        if (CollectionUtils.isEmpty(plantOrders) && CollectionUtils.isEmpty(plantOrderAssignMap))
            return new ArrayList<>();

        // Get list Opt message for Do Plans
        Map<String, String> optForOrderNos = new HashMap<>();
//                CollectionUtils.isEmpty(plantOrders) ? null :
//                orderOptMessageRepository.findAllByOrderIds(orderNoSearch).stream()
//                .collect(Collectors.toMap(o -> (String.valueOf(o.getOrder().getId()) + "_" + o.getRegion_id() + "_" + o.getLoadNo()), o -> o.getMessage()));

        Set<String> halfAssignSet = new HashSet<>();

        for (OrderPlanResponse o : plantOrders) {
            o.populateDoGroup(plantMap, optForOrderNos);

            // Thanh add available assign DO plant to Plan Order
            OrderPlanResponse assign = plantOrderAssignMap.get(o.getOrderIdNoRegion());
            o.setEarliestDeliveryOrderPlanStartTime();
            if (assign != null) {
                o.getDoPlans().addAll(assign.getDoPlans());
                halfAssignSet.add(o.getOrderIdNoRegion());
            }
        };

        // Thanh add only assign DO plant to Plan Order
        plantOrderAssignMap.forEach((k,v) -> {
            if (!halfAssignSet.contains(k)) {
                plantOrders.add(v);
            }
        });

        List<OrderPlanResponse> result = plantOrders
                .stream()
                .filter(order -> order.getDoPlans()!=null && !order.getDoPlans().isEmpty())
                .collect(Collectors.toList());

        return result;
    }
}
