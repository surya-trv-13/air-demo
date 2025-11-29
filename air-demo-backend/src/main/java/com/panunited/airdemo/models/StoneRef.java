package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Table(name="PUMP_STONE")
@Embeddable
@Data
@AllArgsConstructor
public class StoneRef {
    @Column(name = "STONE_ID")
    private Long stoneId;
}
