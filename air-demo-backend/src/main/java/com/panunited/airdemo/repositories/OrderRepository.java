package com.panunited.airdemo.repositories;

import com.panunited.airdemo.dto.OrderListResponse;
import com.panunited.airdemo.dto.OrderListResponseProjection;
import com.panunited.airdemo.dto.OrderTruckItem;
import com.panunited.airdemo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
            WITH ORDER_PLANT_SELECT AS (
                         SELECT
                           *
                         FROM
                           (
                             SELECT
                               OP.ORDER_ID,
                               OP.PLANT_ID,
                               OP.START_TIME,
                               ROW_NUMBER() OVER (
                                 PARTITION BY OP.ORDER_ID
                                 ORDER BY
                                   OP.ORDER_ID DESC
                               ) AS ROW_ORDER
                             FROM
                               ORDER_PLANT OP
                             INNER JOIN ORDERS O ON OP.order_id = O.id
                             WHERE
                               OP.PLANT_TYPE like '%MAIN_PLANT%' AND O.ORDER_DATE = :orderDate
                           ) CC
                         WHERE
                           ROW_ORDER = 1
                       ),
                       MERGE_ORDER_DETAIL AS (
                         SELECT
                           ID AS ORDER_ID,
                           CUSTOMER_ID,
                           PROJECT_ID,
                           PRODUCT_ID,
                           MERGE_ORDER_ID,
                           ADDRESS,
                           TECHNICIAN_ON_SITE,
                           ROW_NUMBER() OVER (
                             PARTITION BY MERGE_ORDER_ID
                             ORDER BY
                               MERGE_ORDER_ID
                           ) AS ROW_ORDER_OF_MERGE_ORDER
                         FROM
                           ORDERS
                         WHERE
                           MERGE_ORDER_ID IS NOT NULL
                           AND ORDER_DATE = :orderDate
                       ),
                       TOTAL_ORDER_VIEW AS (
                         SELECT
                           MO.ID,
                           (
                             SELECT
                               STRING_AGG(ORDER_NO, '$')
                             FROM
                               ORDERS
                             WHERE
                               MERGE_ORDER_ID = MO.ID
                           ) AS ORDER_NO,
                           (
                             SELECT
                               STRING_AGG(CT_ORDER_CODE, '$')
                             FROM
                               ORDERS
                             WHERE
                               MERGE_ORDER_ID = MO.ID
                           ) AS CT_ORDER_CODE,
                           MO.STATUS,
                           MO.ORDER_DATE,
                           (
                             SELECT
                               STRING_AGG(ADDRESS, '$')
                             FROM
                               ORDERS
                             WHERE
                               MERGE_ORDER_ID = MO.ID
                           ) AS ADDRESS,
                           MO.TOTAL_QUANTITY AS ORDER_QUANTITY,
                           '6' AS QUANTITY_PER_LOAD,
                           MO.START_TIME,
                           MO.CREATED_DATE,
                           MO.CREATED_BY,
                           MOD.CUSTOMER_ID,
                           MOD.PROJECT_ID,
                           MOD.PRODUCT_ID,
                           MOD.TECHNICIAN_ON_SITE,
                           OPS.PLANT_ID MAIN_PLANT_ID,
                           OPS.START_TIME BATCH_TIME,
                           CAST(1 as bit) AS MERGING_ORDER,
                           '-1' AS ORDER_ID,
                           MO.ID AS MERGE_ORDER_ID,
                           MO.MULTIPLE_DELIVERY_ORDER,
                           MO.CONTINUOUS_ASSIGN_DELIVERY_ORDER,
                           NULL AS LAYER_GROUP,
                           NULL AS ASSOCIATE_PRODUCT_AGG,
                           NULL AS IS_BOOKING,
                           CAST(
                             (
                               SELECT
                                 CASE WHEN COUNT(IS_CALLED_DO) = SUM(
                                   CAST(IS_CALLED_DO AS INT)
                                 ) THEN 1 ELSE 0 END
                               FROM
                                 ORDERS
                               WHERE
                                 MERGE_ORDER_ID = MO.ID
                             ) as bit
                           ) AS CALLED_DELIVERY_ORDER,
                           NULL AS MISSING_INFO_RESULT,
                           NULL AS INTERVALS,
                           NULL AS LATITUDE,
                           NULL AS LONGITUDE,
                           (SELECT IIF(COUNT(*) > 0, MO.TOTAL_QUANTITY, 0)
                            FROM DELIVERY_ORDER DO
                            WHERE DO.STATUS NOT IN ('EMOD_CANCEL')
                          	   AND DO.MERGE_ORDER_ID = MO.ID) AS PROGRESSIVE_QUANTITY,
            
                           (SELECT IIF(COUNT(*) > 0, MO.TOTAL_QUANTITY, 0)
                            FROM DELIVERY_ORDER DO
                            WHERE DO.STATUS NOT IN ('EMOD_CANCEL')
                          	   AND DO.DO_NUMBER IS NOT NULL
                          	   AND DO.MERGE_ORDER_ID = MO.ID) AS ACTUAL_PROGRESSIVE_QUANTITY,
                          	NULL AS PARENT_ORDER_ID
                         FROM
                           MERGE_ORDER MO
                           LEFT JOIN MERGE_ORDER_DETAIL MOD ON MO.ID = MOD.MERGE_ORDER_ID
                           LEFT JOIN ORDER_PLANT_SELECT OPS ON MOD.ORDER_ID = OPS.ORDER_ID
                         WHERE
                           MOD.ROW_ORDER_OF_MERGE_ORDER = 1
                           AND MO.ORDER_DATE = :orderDate
                         UNION
                         SELECT
                           O.ID,
                           O.ORDER_NO,
                           O.CT_ORDER_CODE,
                           O.STATUS,
                           O.ORDER_DATE,
                           O.ADDRESS,
                           O.ORDER_QUANTITY,
                           '6' AS QUANTITY_PER_LOAD,
                           O.START_TIME,
                           O.CREATED_DATE,
                           O.CREATED_BY,
                           O.CUSTOMER_ID,
                           O.PROJECT_ID,
                           O.PRODUCT_ID,
                           O.TECHNICIAN_ON_SITE,
                           OPS.PLANT_ID MAIN_PLANT_ID,
                           OPS.START_TIME BATCH_TIME,
                           CAST(0 as bit) AS MERGING_ORDER,
                           O.ID AS ORDER_ID,
                           '-1' AS MERGE_ORDER_ID,
                           NULL AS MULTIPLE_DELIVERY_ORDER,
                           O.CONTINUOUS_ASSIGN_DELIVERY_ORDER,
                           O.LAYER_GROUP,
                           (
                             SELECT
                               STRING_AGG(OAP.associate_product_id, '#') WITHIN GROUP (
                                 ORDER BY
                                   OAP.associate_product_id
                               )
                             FROM
                               (
                                 select
                                   distinct associate_product_id,
                                   quantity,
                                   order_id
                                 from
                                   ORDER_ASSOCIATE_PRODUCT
                               ) OAP
                             WHERE
                               O.ID = OAP.ORDER_ID
                           ) AS ASSOCIATE_PRODUCT_AGG,
                           O.IS_BOOKING,
                           O.IS_CALLED_DO AS CALLED_DELIVERY_ORDER,
                           O.MISSING_INFO_RESULT,
                           O.INTERVALS,
                           O.LATITUDE,
                           O.LONGITUDE,
                       	COALESCE(O.ASSIGNED_QUANTITY, 0) AS PROGRESSIVE_QUANTITY,
                       	COALESCE(O.PROGRESSIVE_QUANTITY, 0) AS ACTUAL_PROGRESSIVE_QUANTITY,
                       	O.PARENT_ORDER_ID
                         FROM
                           ORDERS O
                           LEFT JOIN ORDER_PLANT_SELECT OPS ON O.ID = OPS.ORDER_ID
                         WHERE
                           O.MERGE_ORDER_ID IS NULL
                           AND O.ORDER_DATE = :orderDate
                       )
                       SELECT
                         TOV.*,
                         CUST.CUSTOMER_NAME,
                         CUST.CUSTOMER_CODE,
                         PROJ.PROJECT_NAME,
                         PROD.PRODUCT_CODE,
                         PROD.PRODUCT_DESCRIPTION,
                         PLT.PLANT_NAME MAIN_PLANT_NAME,
                         PLT.REGION_ID,
                         R.REGION_NAME,
                         CAST (
                           CASE WHEN (
                             (
                               TOV.ORDER_QUANTITY - COALESCE(TOV.PROGRESSIVE_QUANTITY, 0)
                             ) > 0
                             AND TOV.STATUS = 'ACCEPTED'
                             AND CUST.CUSTOMER_STATUS = 'ACTIVE'
                             AND PROJ.ACTIVE = 1
                             AND TOV.PRODUCT_ID IS NOT NULL
                           ) THEN 1 ELSE 0 END as BIT
                         ) AS IS_ASSIGNABLE,
                         CAST (
                           CASE WHEN (
                             (
                               TOV.ORDER_QUANTITY - COALESCE(TOV.PROGRESSIVE_QUANTITY, 0)
                             ) > 0
                             AND TOV.STATUS IN ('ACCEPTED', 'PENDING')
                             AND TOV.MERGING_ORDER = 0
                           ) THEN 1 ELSE 0 END as BIT
                         ) AS IS_EDITABLE,
                         (
                           SELECT
                             CAST(IIF(COUNT(*) > 0, 1, 0) as BIT)
                           FROM
                             DELIVERY_ORDER DO
                           WHERE
                             ((TOV.ORDER_ID <> -1 AND TOV.ORDER_ID = DO.ORDER_ID)
                             OR (TOV.MERGE_ORDER_ID <> -1 AND TOV.MERGE_ORDER_ID = DO.ORDER_ID))
                             AND DO.STATUS IN ('INVOICED', 'EXPORTED')
                         ) AS HAS_INVOICED_DO,
                         (
                           SELECT
                             CAST(IIF(COUNT(*) > 0, 1, 0) as BIT)
                           FROM
                             DELIVERY_ORDER DO
                           WHERE
                             ((TOV.ORDER_ID <> -1 AND TOV.ORDER_ID = DO.ORDER_ID)
                             OR (TOV.MERGE_ORDER_ID <> -1 AND TOV.MERGE_ORDER_ID = DO.ORDER_ID))
                             AND INVOICE_DRAFT = 1
                         ) AS HAS_INVOICE_DRAFT_GENERATED_DO,
                         OCT.TEST_TYPE,
                         OCT.HAVE_CERT
                       FROM
                         TOTAL_ORDER_VIEW TOV
                         JOIN CUSTOMER CUST ON TOV.CUSTOMER_ID = CUST.ID
                         JOIN PROJECT PROJ ON TOV.PROJECT_ID = PROJ.ID
                         LEFT JOIN PRODUCT PROD ON TOV.PRODUCT_ID = PROD.ID
                         LEFT JOIN PLANT PLT ON PLT.ID = TOV.MAIN_PLANT_ID
                         LEFT JOIN REGION R ON PLT.REGION_ID = R.REGION_ID
                         LEFT JOIN ORDER_CYLINDER_TEST OCT ON TOV.ORDER_ID = OCT.ORDER_ID
                       WHERE
                         (	
            			   TOV.MAIN_PLANT_ID IS NOT NULL
                           AND PLT.REGION_ID = :regionId )
                           OR
                         ( TOV.MAIN_PLANT_ID IS NULL
            			   AND TOV.IS_BOOKING = 1
            			   AND :regionId = 1
            			 )
                         AND TOV.PARENT_ORDER_ID IS NULL
                       ORDER BY
                         TOV.START_TIME ASC
            """, nativeQuery = true)
    List<OrderListResponseProjection> findOrderListByOrderDateAndRegionId(LocalDate orderDate, Long regionId);

    @Query(value = """
			SELECT OT.ORDER_ID, T.ID AS TRUCK_ID, T.TRUCK_NO, T.TRUCK_NAME
			FROM ORDER_TRUCK OT
			         INNER JOIN TRUCK T ON OT.TRUCK_ID = T.ID
			WHERE OT.ORDER_ID IN (:orderIds)
			UNION
			SELECT OT.ORDER_ID, T.ID AS TRUCK_ID, T.TRUCK_NO, T.TRUCK_NAME
			FROM ORDER_TRUCK OT
			         INNER JOIN FLEET_GROUP FG ON FG.ID = OT.TRUCK_GROUP_ID
			         INNER JOIN FLEET_GROUP_TRUCK FGT ON FGT.GROUP_ID = FG.ID
			         INNER JOIN TRUCK T ON FGT.TRUCK_ID = T.ID
			WHERE OT.ORDER_ID IN (:orderIds)
			""", nativeQuery = true)
    List<OrderTruckItem> findAllTruck(List<Long> orderIds);

	@Modifying
	@Query(value = """
			UPDATE ORDERS
			SET LAYER_SEQUENCE = :layerSequence,  LAYER_GROUP = :orderId
			WHERE ID = :orderId
			""", nativeQuery = true)
	void updateFirstLayerOrder(Integer layerSequence, Long orderId);

	@Query(value = """
			SELECT TOP 1 LAYER_SEQUENCE
			FROM ORDERS
			WHERE LAYER_GROUP =
				(SELECT LAYER_GROUP FROM ORDERS WHERE ID =:orderId)
			ORDER BY LAYER_SEQUENCE DESC
			""", nativeQuery = true)
	Integer findLastLayerOrderSequence(Long orderId);
	List<Order> findByLayerGroup(Long layerGroup);

	@Query(value = """
			SELECT TOP 2 O.* FROM Orders O
			""", nativeQuery = true)
    List<Order> findFirst2ByOrderByIdAsc();

	Order findByCreatedBy (long userId);
}
