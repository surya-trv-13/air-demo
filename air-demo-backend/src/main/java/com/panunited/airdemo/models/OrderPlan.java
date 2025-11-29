package com.panunited.airdemo.models;

import java.time.LocalDateTime;
import java.util.Optional;


import com.panunited.airdemo.utils.DateTimeUtil;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class OrderPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERGE_ORDER_ID")
    private MergeOrder mergeOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REGION_ID")
    private Region region;
    private String details;
    private LocalDateTime createdDate;
    private String identifier;

    public OrderPlan() {
        this.createdDate = DateTimeUtil.getCurrentLocalDateTime();
    }
}

