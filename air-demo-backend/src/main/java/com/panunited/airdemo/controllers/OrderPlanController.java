package com.panunited.airdemo.controllers;

import com.panunited.airdemo.dto.OrderPlanResponse;
import com.panunited.airdemo.dto.OrderPlanSearchParams;
import com.panunited.airdemo.service.OrderPlanService;
import com.panunited.airdemo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-plan")
@AllArgsConstructor
public class OrderPlanController  extends  BaseController{

    private final OrderPlanService orderPlanService;

    @GetMapping("plans")
    public ResponseEntity<List<OrderPlanResponse>> getOrderPlans(OrderPlanSearchParams params) {
        List<OrderPlanResponse> orderPlanList = orderPlanService.getOrderPlanResponse(params);
        return ResponseEntity.ok(orderPlanList);
    }
}
