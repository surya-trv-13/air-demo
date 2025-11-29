package com.panunited.airdemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Pump extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private Boolean active;

    @ElementCollection
    @CollectionTable(
            name = "PUMP_STONE",
            joinColumns = @JoinColumn(name = "PUMP_ID")
    )
    private Set<StoneRef> stoneList = new HashSet<>();

    public Set<Long> getStoneIds() {
        return stoneList.stream().map(StoneRef::getStoneId).collect(Collectors.toSet());
    }
}

