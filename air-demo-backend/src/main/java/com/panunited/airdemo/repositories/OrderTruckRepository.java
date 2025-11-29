package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.OrderTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderTruckRepository extends JpaRepository<OrderTruck, Long> {
    @Modifying
    @Query(value = """
			delete from order_truck 
			where order_id = :orderId
			""", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);
}
