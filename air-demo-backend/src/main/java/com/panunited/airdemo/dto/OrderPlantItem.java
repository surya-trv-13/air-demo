package com.panunited.airdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panunited.airdemo.enums.PlantType;
import com.panunited.airdemo.models.Order;
import com.panunited.airdemo.models.OrderPlant;
import com.panunited.airdemo.models.Plant;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Getter
@Setter
@Validated
public class OrderPlantItem {

    private Long id;
    @NotNull(message = "Invalid Assigned Plant: plantType is null")
    private String plantType;
    @NotNull(message = "Invalid Assigned Plant: plant is null")
    private Long plantId;
    @NotNull(message = "Invalid Assigned Plant: quantity is null")
    @Min(value = 0, message = "Invalid Assigned Plant: quantity must be at least 0")
    private Double quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime startTime;
    @Min(value = 0, message = "Invalid Assigned Plant: intervals must be at least 0")
    private Integer intervals;
    private Long orderId;

    public OrderPlant changeToOrderPlant(Order order, Plant plant) {
        OrderPlant orderPlant = new OrderPlant();
        orderPlant.setPlantType(PlantType.valueOf(this.plantType));
        orderPlant.setPlant(plant);
        orderPlant.setQuantity(this.quantity);
        orderPlant.setStartTime(DateTimeUtil.convertCurrentSystemToUtcTimeZone(this.startTime));
        orderPlant.setIntervals(this.intervals);
        orderPlant.setOrder(order);

        if(id != null) {
            orderPlant.setId (id);
        }
        return orderPlant;
    }

    public void setMainPlant (Long plantId,
                              Double orderQuantity,
                              LocalDateTime startTime,
                              Integer intervals) {
        this.plantType = String.valueOf(PlantType.MAIN_PLANT);
        this.plantId = plantId;
        this.quantity = orderQuantity;
        this.startTime = startTime;
        this.intervals = intervals;
    }

    public void setSupportPlant (Long plantId,
                                 Double orderQuantity,
                                 LocalDateTime startTime,
                                 Integer intervals) {
        this.plantType = String.valueOf(PlantType.SUPPORT_PLANT);
        this.plantId = plantId;
        this.quantity = orderQuantity;
        this.startTime = startTime;
        this.intervals = intervals;
    }
}
