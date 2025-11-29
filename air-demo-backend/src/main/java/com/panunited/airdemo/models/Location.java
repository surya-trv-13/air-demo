package com.panunited.airdemo.models;


import com.panunited.airdemo.enums.LocationType;
import jakarta.persistence.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Location extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
    @NotNull
    private Long projectId;

    private String geoBoundary;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private LocationType type;

}
