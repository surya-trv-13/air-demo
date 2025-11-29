package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.CylinderTestType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderCylinderTest  extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CylinderTestType testType;
    private Integer standardTestNumber;
    @OneToMany(mappedBy = "orderCylinderTest", cascade = CascadeType.ALL)
    private Set<OrderCylinderTestAge> testAgeSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;
    private int acceptedCount;
    private boolean haveCert;
    private boolean hitTopSixProduct;


    public Long getOrder() { return Optional.ofNullable(this.order).map(Order::getId).orElse(null); }
}
