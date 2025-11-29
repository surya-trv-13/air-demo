package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.AuditOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditOrderRepository extends JpaRepository<AuditOrder, Long> {
}
