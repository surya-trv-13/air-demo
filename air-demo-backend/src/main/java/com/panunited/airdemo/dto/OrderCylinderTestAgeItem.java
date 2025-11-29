package com.panunited.airdemo.dto;

import com.panunited.airdemo.models.OrderCylinderTestAge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCylinderTestAgeItem {

    private Integer numberOfCylinder;
    private Integer testAge;
    private String uom;
    private Boolean addedBackFromDoPage;
    public OrderCylinderTestAge changeToOrderCylinderTestAge() {
        OrderCylinderTestAge orderCylinderTestAge = new OrderCylinderTestAge();
        orderCylinderTestAge.setNumberOfCylinder(this.numberOfCylinder);
        orderCylinderTestAge.setTestAge(this.testAge);
        orderCylinderTestAge.setUom(this.uom);
        orderCylinderTestAge.setAddedBackFromDoPage(this.addedBackFromDoPage);
        return orderCylinderTestAge;
    }

}
