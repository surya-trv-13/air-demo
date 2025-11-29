package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.OrderAssociateProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface OrderAssociateProductRepository extends JpaRepository<OrderAssociateProduct, Long> {
    @Query(value = """
			SELECT OAP.* FROM ORDER_ASSOCIATE_PRODUCT AS OAP where OAP.ORDER_ID = :orderId
			""", nativeQuery = true)
    List<OrderAssociateProduct> findOrderAssociateProductByOrderId(long orderId);
}
