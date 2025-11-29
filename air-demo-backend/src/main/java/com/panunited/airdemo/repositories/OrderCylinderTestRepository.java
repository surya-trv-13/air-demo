package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.OrderCylinderTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderCylinderTestRepository extends JpaRepository<OrderCylinderTest, Long> {

    @Modifying
    @Query(value = """
			DELETE FROM ORDER_CYLINDER_TEST_AGE
			WHERE ORDER_CYLINDER_TEST_ID = (SELECT ID FROM ORDER_CYLINDER_TEST WHERE ORDER_ID =:orderId);
			DELETE FROM ORDER_CYLINDER_TEST WHERE ORDER_ID =:orderId;
			""", nativeQuery = true)
    void deleteCylinderTestAndAgeByOrderId(Long orderId);
}
