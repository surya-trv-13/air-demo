package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.PurchaseOrderDto;
import com.panunited.airdemo.exception.InvalidRequestException;
import com.panunited.airdemo.models.PurchaseOrder;
import com.panunited.airdemo.repositories.PurchaseOrderRepository;
import com.panunited.airdemo.utils.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public Long createOrderPO(String purchaseOrderNumber, Long projectId) throws Exception {
        List<PurchaseOrderDto> savedPurchaseOrders = purchaseOrderRepository.findByPurchaseOrderNumberAndProjectId(purchaseOrderNumber, projectId);
        if (savedPurchaseOrders.isEmpty()) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderNumber(purchaseOrderNumber);
            purchaseOrder.setPurchaseOrderDate(DateTimeUtil.getCurrentLocalDate());
            purchaseOrder.setProjectId(projectId);
            purchaseOrder.setActive(true);
            return purchaseOrderRepository.save(purchaseOrder).getId();
        } else if (savedPurchaseOrders.size() >= 1) {
            // to get only active PO only from duplicated PO list
            List<PurchaseOrderDto> activePurchaseOrderList = savedPurchaseOrders.stream().filter(PurchaseOrderDto::isActive)
                    .toList();
            if (!activePurchaseOrderList.isEmpty()) {
                PurchaseOrderDto purchaseOrder = activePurchaseOrderList.get(0);
                return purchaseOrder.getId();
            } else {
                throw new InvalidRequestException("The Purchase Order Number already exists but is inactive. Please contact sales-in-charge to reactivate it.");
            }
        } else {
            PurchaseOrderDto savedPurchaseOrder = savedPurchaseOrders.get(0);
            if (!savedPurchaseOrder.isActive()) {
                throw new InvalidRequestException("The Purchase Order Number already exists but is inactive. Please contact sales-in-charge to reactivate it.");
            }
            return savedPurchaseOrder.getId();
        }
    }
}
