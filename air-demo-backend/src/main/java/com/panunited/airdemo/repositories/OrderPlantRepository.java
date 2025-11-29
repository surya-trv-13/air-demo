package com.panunited.airdemo.repositories;

import com.panunited.airdemo.dto.OrderPlantItem;
import com.panunited.airdemo.dto.OrderPlantItemProjection;
import com.panunited.airdemo.models.OrderPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderPlantRepository extends JpaRepository<OrderPlant, Long> {
    @Modifying
    @Query(value = """
			DELETE FROM ORDER_PLANT WHERE ORDER_ID =:orderId
			""", nativeQuery = true)
    void deleteAllByOrderId (Long orderId);

    @Query(value = """
			SELECT ID, PLANT_TYPE, PLANT_ID, QUANTITY, START_TIME, INTERVALS, ORDER_ID
			FROM ORDER_PLANT
			WHERE ORDER_ID IN (:orderIds)
			ORDER BY ID
			""", nativeQuery = true)
    List<OrderPlantItemProjection> findAllByOrderIds (@Param("orderIds") List<Long> orderIds);
}
