package com.panunited.airdemo.controllers;

import com.panunited.airdemo.dto.OrderDetailListResponse;
import com.panunited.airdemo.dto.OrderSaveRequest;
import com.panunited.airdemo.dto.OrderSaveResponse;
import com.panunited.airdemo.security.models.UserPrincipal;
import com.panunited.airdemo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController extends BaseController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDetailListResponse>> getAllOrders (@AuthenticationPrincipal UserPrincipal principal, @RequestParam("orderDateStart") LocalDate orderDateStart,@RequestParam("orderDateEnd") LocalDate orderDateEnd, @RequestParam("regionIds") List<Long> regionIds) throws Exception {
        List<OrderDetailListResponse> orderDetailedList = new ArrayList<>();
        Long userId = getLoginUserId(principal);

        for(Long regionId: regionIds) {
            orderDetailedList.addAll(orderService.getAllOrders(orderDateStart,orderDateEnd, regionId, userId));
        }

        return ResponseEntity.ok(orderDetailedList);
    }

    @PostMapping
    public ResponseEntity<OrderSaveResponse> createOrder(@AuthenticationPrincipal UserPrincipal principal, @RequestBody OrderSaveRequest orderSaveRequest) throws Exception {
        long userId = getLoginUserId(principal);
        OrderSaveResponse response = orderService.saveOrderBooking(orderSaveRequest, userId);

        return ResponseEntity.ok(response);
    }
}
