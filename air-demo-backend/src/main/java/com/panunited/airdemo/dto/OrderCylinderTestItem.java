package com.panunited.airdemo.dto;

import com.panunited.airdemo.enums.CylinderTestType;
import com.panunited.airdemo.models.Order;
import com.panunited.airdemo.models.OrderCylinderTest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderCylinderTestItem {

    private Long id;
    private String testType;
    private Integer standardTestNumber;
    private Set<OrderCylinderTestAgeItem> testAgeSet;
    private boolean haveCert;

    public OrderCylinderTest changeToOrderCylinderTest(Order order) {
        OrderCylinderTest orderCylinderTest = new OrderCylinderTest();
        orderCylinderTest.setTestType(CylinderTestType.valueOf(this.testType));
        orderCylinderTest.setStandardTestNumber(Objects.requireNonNullElse(this.standardTestNumber, 1));
        if (this.testAgeSet != null) {
            orderCylinderTest.setTestAgeSet(this.testAgeSet.stream().map(OrderCylinderTestAgeItem::changeToOrderCylinderTestAge).collect(Collectors.toSet()));
        }
        orderCylinderTest.setOrder(order);
        orderCylinderTest.setHaveCert(this.haveCert);
        orderCylinderTest.setUpdatedDate(LocalDateTime.now());
        return orderCylinderTest;
    }

}
