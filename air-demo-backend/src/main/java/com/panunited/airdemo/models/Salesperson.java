package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Salesperson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salespersonId;
    private String salespersonName;
    private String salespersonContactNumber;
    private String salespersonEmailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REGION_ID")
    private Region region;
}
