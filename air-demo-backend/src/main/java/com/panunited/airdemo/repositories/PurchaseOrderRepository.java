package com.panunited.airdemo.repositories;

import com.panunited.airdemo.dto.PurchaseOrderDto;
import com.panunited.airdemo.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    @Query(value = """
			SELECT ID, ACTIVE
			FROM PURCHASE_ORDER 
			WHERE PROJECT_ID = :projectId AND TRIM(PURCHASE_ORDER_NUMBER) = TRIM(:purchaseOrderNumber)
			""", nativeQuery = true)
    List<PurchaseOrderDto> findByPurchaseOrderNumberAndProjectId(String purchaseOrderNumber, Long projectId);
}
