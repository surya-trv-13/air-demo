package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    @Query(value = """
			SELECT COUNT(*)
			FROM DELIVERY_ORDER
			WHERE ASSIGNED_BP = :batchingPlantId
			AND DO_NUMBER IS NULL
			AND STATUS = 'ASSIGNED'
			""", nativeQuery = true)
    Integer findActiveDeliveryOrderCountByBatchingPlantId(@Param("batchingPlantId") Long batchingPlantId);

    @Query(value = """
			WITH ORDER_QUANTITY AS (
			    SELECT COALESCE(SUM(ORDER_QUANTITY), 0) AS TOTAL_ORDER_QUANTITY
			    FROM ORDERS
			    WHERE MERGE_ORDER_ID = :mergeOrderId
			),
			ASSIGNED_QUANTITY AS (
			    SELECT COALESCE(SUM(COALESCE(ED.NEW_QUANTITY, D.DO_QUANTITY)), 0) AS TOTAL_ASSIGNED_QUANTITY
			    FROM DELIVERY_ORDER D
			    LEFT JOIN EMODIFICATION_DETAIL ED ON D.ID = ED.DELIVERY_ORDER_ID
			        AND ED.STATUS NOT IN ('WITHDRAW', 'REJECTED_CCM', 'REJECTED_FINANCE')
			        AND ED.CREATED_DATE = (SELECT MAX(CREATED_DATE) FROM EMODIFICATION_DETAIL EDM WHERE EDM.DELIVERY_ORDER_ID = D.ID)
			    WHERE MERGE_ORDER_ID = :mergeOrderId
			)
			SELECT TOTAL_ORDER_QUANTITY - TOTAL_ASSIGNED_QUANTITY
			FROM ORDER_QUANTITY, ASSIGNED_QUANTITY;
			""", nativeQuery = true)
    Double getUnassignedOrderQuantityForMergeOrder(Long mergeOrderId);

	DeliveryOrder findByDoNumber(String doNumber);
}
