package com.panunited.airdemo.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class OrderAssociateProduct extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ASSOCIATE_PRODUCT_ID", insertable = false, updatable = false)
    private Long associateProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ASSOCIATE_PRODUCT_ID")
    private AssociateProduct associateProduct;

    @Column(name="MATERIAL_ID")
    private Long materialId;
    private Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID", insertable = false, updatable = false)
    private Order order;
    private Long bundledWith;


    public Long getOrder() { return Optional.ofNullable(this.order).map(item -> item.getId()).orElse(null); }

    public boolean isSameData(OrderAssociateProduct orderAssociateProduct) {
        if (this == orderAssociateProduct)
            return true;
        if (orderAssociateProduct == null)
            return false;
        return Objects.equals(id, orderAssociateProduct.id)
                && Objects.equals(order, orderAssociateProduct.order)
                && Objects.equals(associateProductId, orderAssociateProduct.associateProductId)
                && Objects.equals(quantity, orderAssociateProduct.quantity);
    }

}
