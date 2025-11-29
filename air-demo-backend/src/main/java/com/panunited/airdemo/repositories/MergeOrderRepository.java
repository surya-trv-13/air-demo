package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.MergeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MergeOrderRepository extends JpaRepository<MergeOrder, Long> {
}
