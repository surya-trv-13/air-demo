package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.TruckType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TruckTypeRepository extends JpaRepository<TruckType, Long> {
    @Query(value = """
            SELECT MAX(CAPACITY) FROM TRUCK_TYPE WHERE IS_ACTIVE = 1
            """, nativeQuery = true)
    Double getMaximumCapacity();
}
