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
public class TruckType extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Double capacity;

    @Column(name="IS_ACTIVE")
    private boolean active;

    @Column(name="IS_MAIN")
    private boolean main;

    public static TruckType init(String type, Double capacity, boolean main, boolean active, Long creator) {
        TruckType truckType = new TruckType();
        truckType.active = active;
        truckType.capacity = capacity;
        truckType.type = type;
        truckType.main = main;
        truckType.setCreatedBy(creator);
        return truckType;
    }

    public void fill(String type, Double capacity, boolean main, boolean active, Long updaterId) {
        this.active = true;
        this.capacity = capacity;
        this.type = type;
        this.main = main;
        this.setUpdatedBy(updaterId);
    }
}
