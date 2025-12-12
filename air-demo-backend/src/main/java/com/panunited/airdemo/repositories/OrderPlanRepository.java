package com.panunited.airdemo.repositories;

import com.panunited.airdemo.dto.OrderPlanAssigned;
import com.panunited.airdemo.dto.OrderPlanAssignedProjection;
import com.panunited.airdemo.dto.OrderPlanSearchParams;
import com.panunited.airdemo.models.OrderPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderPlanRepository extends JpaRepository<OrderPlan, Long> {
    @Modifying
    @Query(value = "DELETE FROM ORDER_PLAN WHERE ORDER_ID =:orderId", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);

    @Query(value = """ 
                SELECT DISTINCT oh.id order_id, oh.order_no, od.assigned_time start_time, od.load_number
                    ,oh.progressive_quantity,oh.order_quantity, od.do_quantity delivery_quantity, loc.name location_name
                    ,plt.plant_name, od.assigned_bp batching_plant_id, plt.region_id, re.region_code, plt.plant_code, plt.id plant_id
                    ,cus.customer_name, prod.product_description, proj.project_name, prod.product_code
                FROM delivery_order od
                    INNER JOIN orders oh ON od.order_id = oh.id
                    INNER JOIN customer cus ON cus.id = oh.customer_id
                    INNER JOIN project proj ON proj.id = oh.project_id
                    INNER JOIN product prod ON prod.id = oh.product_id
                    LEFT JOIN location loc ON loc.id = oh.location_id
                    INNER JOIN plant plt ON od.assigned_plant_id = plt.id
                    INNER JOIN region re ON re.region_id = plt.region_id
                WHERE 1=1
                AND od.status = 'ASSIGNED'
                AND assigned_plant_id IS NOT NULL
                AND assigned_bp IS NOT NULL
                AND assigned_time IS NOT NULL
                AND (oh.status IS NULL OR (oh.status != 'CANCEL' AND oh.status != 'HOLD'))
                AND (oh.is_booking IS NULL OR oh.is_booking = 0)
                AND oh.order_date >=  DATEADD(DAY, -3, CURRENT_TIMESTAMP )
                AND cus.id = CASE WHEN :customerId IS NULL THEN cus.id ELSE :customerId END
                AND proj.id = CASE WHEN :projectId IS NULL THEN proj.id ELSE :projectId END
                AND plt.region_id = CASE WHEN :regionId IS NULL THEN plt.region_id ELSE :regionId END
                ORDER BY start_time
            """, nativeQuery = true)
    List<OrderPlanAssigned> getOrderPlanAssigns(Long customerId, Long projectId, Long regionId);



    @Query(value = """
            SELECT o.id order_id, o.order_no, c.customer_name, l.name location_name, p.project_name, pl.plant_name, pl.plant_code, pl.id plant_id, 
                o.order_quantity, r.region_code, r.region_id, pr.product_code, pr.product_description, o.intervals, o.order_date_time_utc start_time
            FROM orders o
            LEFT JOIN customer c ON o.customer_id = c.id
            LEFT JOIN location l ON o.location_id = l.id
            LEFT JOIN project p ON o.project_id = p.id
            INNER JOIN order_plant op ON o.id = op.order_id
            LEFT JOIN plant pl ON op.plant_id = pl.id
            LEFT JOIN region r ON r.region_id = pl.region_id
            LEFT JOIN product pr ON pr.id = o.product_id
            WHERE o.id IN (26, 27, 30, 31, 32, 33)
            AND (:customerId IS NULL OR c.id = :customerId)
             AND (:locationId IS NULL OR l.id = :locationId)
            AND (:projectId IS NULL OR p.id = :projectId)
            """, nativeQuery = true)
    List<OrderPlanAssignedProjection> getOrderPlansAssign(Long customerId, Long locationId, Long projectId);

    @Query(value = """
                SELECT OH.id order_id
                	,OH.order_no
                    ,OH.order_quantity
                    ,OH.intervals
                    ,cus.customer_name
                    ,proj.project_name
                    ,loc.name location_name
                    ,OP.details
                    ,OPA.plant_code
                    ,prod.product_code
                    ,prod.product_description
                    ,OPA.plant_name
                    ,OPA.plant_id
                    ,OPA.region_id
                    ,RE.region_code
                    ,OH.quantity_per_load doQuantity
                FROM order_plan OP
                INNER JOIN orders oh ON Oh.id = OP.order_id
                INNER JOIN (
                    SELECT *
                        FROM (
                        SELECT oh.id order_id
                        ,plt.region_id
                        ,plt.plant_code
                        ,plt.plant_name
                        ,plt.id plant_id
                        ,row_number() OVER (
                    PARTITION BY oh.id
                        ,plt.region_id ORDER BY oh.id
                            ,plt.region_id
                        ) AS roworder
                    FROM orders oh
                    INNER JOIN order_plant opa ON oh.id = opa.order_id
                    INNER JOIN plant plt ON opa.plant_id = plt.id
                    WHERE 1=1
                    AND (( oh.status <> 'CANCEL'
                                    AND oh.status <> 'HOLD' )
                    OR oh.status IS NULL )
                    AND oh.order_date >= DATEADD(DAY, - 3, CURRENT_TIMESTAMP)
                    AND ( opa.quantity != NULL
                            OR opa.quantity != 0 )
                                    ) TEMP
                    WHERE roworder = 1
                                ) OPA ON OP.order_id = OPA.order_id
                    AND OP.region_id = OPA.region_id
                INNER JOIN region RE ON RE.region_id = OP.region_id
                INNER JOIN customer cus ON cus.id = oh.customer_id
                INNER JOIN project proj ON proj.id = oh.project_id
                INNER JOIN product prod ON prod.id = oh.product_id
                LEFT JOIN location loc ON loc.id = oh.location_id
                WHERE 1 = 1
                    AND ISNULL(OH.progressive_quantity, 0) < OH.order_quantity
                    AND cus.id = CASE WHEN :customerId IS NULL THEN cus.id ELSE :customerId END
                    AND proj.id = CASE WHEN :projectId IS NULL THEN proj.id ELSE :projectId END
                    AND OPA.region_id = CASE WHEN :regionId IS NULL THEN OPA.region_id ELSE :regionId END
                ORDER BY oh.id
            """, nativeQuery = true )
    List<OrderPlanAssigned> getOrderPlans(Long customerId, Long projectId, Long regionId);
}
