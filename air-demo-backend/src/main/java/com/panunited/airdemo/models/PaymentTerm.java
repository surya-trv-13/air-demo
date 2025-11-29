package com.panunited.airdemo.models;

import com.panunited.airdemo.enums.PaymentTermType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentTerm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTermId;

    @Enumerated(EnumType.STRING)
    private PaymentTermType paymentTermType;
    private Double paymentTermValue;
    private String paymentTermName;
}
