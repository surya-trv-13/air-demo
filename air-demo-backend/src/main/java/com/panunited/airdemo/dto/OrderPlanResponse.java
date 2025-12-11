package com.panunited.airdemo.dto;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import com.panunited.airdemo.models.Plant;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Mapper
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlanResponse {
    private String orderNo;
    private Long orderId;
    private String deliveryOrderNo;
    private String customerName;
    private String locationName;
    private String projectName;
    private String plantName;
    private String details;
    private String plantCode;
    private Long plantId;
    private Double orderQuantity;
    private Double quantity;
    private Long regionId;
    private String regionCode;
    private String message;
    private List<DoPlan> doPlans = new ArrayList<>();
    private String productCode;
    private String productDescription;
    private Integer intervals;
    private Date earliestDeliveryOrderPlanStartTime;
    private Date startTime;

    public OrderPlanResponse populateDoGroup(Map<Long, Plant> plantMap, Map<String, String> optForOrderNos) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!StringUtils.isEmpty(this.details)) {
                List<DoPlanTable> plansFromTable = mapper.readValue(this.details, new TypeReference<List<DoPlanTable>>(){});
                details = null;

                doPlans = plansFromTable
                        .stream()
                        .map(plan -> {
                            DoPlan doPlan = new DoPlan(plan, regionId, plantId, plantMap);
                            String message = optForOrderNos.get(getOrderIdNoRegion() + "_" + plan.getDeliveryOrderNo());
                            if (!StringUtils.isEmpty(message)) {
                                doPlan.setMessage(message);
                            }
                            return doPlan;
                        })
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            log.error("PlanOrder.populateDoGroup", e);
        }

        return this;
    }

    public OrderPlanResponse addAssignDo(Map<String, List<DoPlan>> halfAssignDo) {
        if (halfAssignDo.containsKey(this.orderNo))
            this.doPlans.addAll(halfAssignDo.get(this.orderNo));
        return this;
    }
    public void addOrderQty(double orderQty) {
        this.orderQuantity += orderQty;
    }

    public String getOrderIdNoRegion() {
        return this.orderId + "_" + this.orderNo + "_" + regionId;
    }

    public void setEarliestDeliveryOrderPlanStartTime () {
        this.earliestDeliveryOrderPlanStartTime = this.doPlans.stream().sorted(Comparator.comparing(DoPlan::getStartTime)).findFirst().get().getStartTime();
    }

}
