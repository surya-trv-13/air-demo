package com.panunited.airdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Material extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long criteriaHeaderId;
    private Long mappedValueId;
    private Long carbonCategoryId;
    private String name;
    private String description;
    private boolean active;
    private Long materialSourceId;
    private Double density;
    private Double moisture;
    private Boolean isAssociateProd;
    private Boolean isMixDesign;
    private Boolean isMaterial;
    private Boolean isOtherProduct;
    private Boolean selectableByCustomer;
    private Boolean requiredDispatcherConfirmation;
    private Boolean alertDriver;
    private String mainAccountCoding;
    private String subAccountCoding;

    @Version
    private int version;

   @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MaterialInfo> materialInfos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MaterialPlant> materialPlants = new LinkedHashSet<>();

    private Long batchCodeId;
    private String batchCodeShortDescription;

    private Long mixDesignUomId;
    private Long orderQuantityUomId;
    private Long deliveryQuantityUomId;
    private Long batchUomId;
    private Long priceUomId;
    private String uomExtensionType;
    private Integer divisor;
    private Double defaultDosageQuantity;
    private Long dosageUomId;
}
