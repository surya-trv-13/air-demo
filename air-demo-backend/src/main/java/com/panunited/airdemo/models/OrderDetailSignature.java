package com.panunited.airdemo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDetailSignature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderDetailId;
    private String signature;
    private String name;

    public OrderDetailSignature(Long orderDetailId, String signature, String name) {
        super();
        this.orderDetailId = orderDetailId;
        this.signature = signature;
        this.name = name;
    }
}
