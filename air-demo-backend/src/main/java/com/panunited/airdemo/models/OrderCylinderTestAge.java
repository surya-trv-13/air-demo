package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderCylinderTestAge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfCylinder;
    private Integer testAge;
    private String uom;
    private Boolean addedBackFromDoPage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_CYLINDER_TEST_ID")
    private OrderCylinderTest orderCylinderTest;

}
