package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.DoPlan;
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

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderPlanService {

    private final OrderPlanMapper mapper;
    private final OrderPlanRepository orderPlanRepository;
    private final PlantRepository plantRepository;

    private static final int DO_QUANTITY = 5;

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

    public List<OrderPlanResponse> getOrderPlanResponse(OrderPlanSearchParams params) {


        try {
             // Step 1 : Fetch the Data for all the Orders
             List<OrderPlanResponse> orderResponseList =  orderPlanRepository.getOrderPlansAssign(params.getCustomerId(), params.getLocationId(), params.getProjectId())
                     .stream().map(mapper::toOrderPlanResponseFromProjection).toList();

             // Step 2: Prepare the DO Plan for the Order created
            for(OrderPlanResponse orderPlanResponse: orderResponseList) {
                orderPlanResponse.setDoPlans(getDOPlanList(orderPlanResponse, params.getTimezone()));
            }

            return  orderResponseList;

        } catch (Exception ee) {
            log.error("Exception Occurred while generating the Order Plan",ee);
        }

        return  new ArrayList<>();
    }

    private List<DoPlan> getDOPlanList (OrderPlanResponse orderPlanResponse, String timezone) {
        List<DoPlan> doPlanList = new ArrayList<>();
        // Step 1: Get Order Plan Response and segregate with the DO Quantity
        // Step 1.1: Count no of DOs possible
        long doCount = (long) (orderPlanResponse.getOrderQuantity() / DO_QUANTITY + (orderPlanResponse.getOrderQuantity()  % DO_QUANTITY > 0 ? 1: 0));
        double progressiveQty = 0.0;

        // Step 2: Prepare DO plan and add to list
        for(int index = 1; index <= doCount; index++) {
           DoPlan doPlan = new DoPlan();
           doPlan.setDeliveryOrderNo(index);

           // Progressive Quantity
           progressiveQty = index < doCount ? progressiveQty + DO_QUANTITY : orderPlanResponse.getOrderQuantity();
           doPlan.setProgressiveQuantity(progressiveQty);

           doPlan.setDeliveryQuantity(doCount == 1 || index < doCount ? DO_QUANTITY : orderPlanResponse.getOrderQuantity() % DO_QUANTITY);

           // StartTime
            Date startTimeAsDate = getStartTimeWithInterval(orderPlanResponse, index, timezone);

            doPlan.setStartTime(startTimeAsDate);

           doPlan.setOrgPlantShortName(orderPlanResponse.getPlantName());
           doPlan.setNewPlantShortName(orderPlanResponse.getPlantName());
           doPlan.setPlantCode(orderPlanResponse.getPlantCode());
           doPlan.setPlantId(orderPlanResponse.getPlantId());
           doPlan.setBatchingPlantId(1L);
           doPlan.setDelay(0);
           doPlan.setIsManualChanged(false);
           doPlan.setIsAssigned(false); // TODO: able to change based on assigned plant
            doPlan.setRegionId(orderPlanResponse.getRegionId());

            doPlanList.add(doPlan);
        }

        // Step 3: Return the list
        return doPlanList;
    }

    private static Date getStartTimeWithInterval(OrderPlanResponse orderPlanResponse, int index, String timezone) {
        Date dbDate = orderPlanResponse.getStartTime();

        // Step 1: Interpret DB value as LOCAL (IST)
        LocalDateTime localWrong = LocalDateTime.ofInstant(
                dbDate.toInstant(),
                ZoneId.of(timezone)
        );

        // Step 2: Correct it — this local time is actually UTC
        ZonedDateTime realUtc = localWrong.atZone(ZoneOffset.UTC);

        // Step 3: Convert UTC → target timezone (IST)
        ZonedDateTime correctLocal = realUtc.withZoneSameInstant(ZoneId.of(timezone));

        // Step 4: Apply interval
        ZonedDateTime updated = correctLocal.plusMinutes(
                (long) (index - 1) * orderPlanResponse.getIntervals()
        );


        System.out.println("orderPlanResponse.getSTartTime():"+ orderPlanResponse.getStartTime());
        System.out.println("JVM timezone: " +ZoneId.systemDefault() );
        System.out.println("Timezone variable value:"+ timezone);

        return Date.from(updated.toInstant());

    }
}
