package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.BatchingPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BatchingPlantRepository extends JpaRepository<BatchingPlant, Long> {
    @Query(value = """
			SELECT TOP 1 BPT.IS_ACTIVE
			FROM BATCHING_PLANT BPT
			WHERE BPT.PLANT_ID = :plantId
			AND BPT.ID = :bpId
			""", nativeQuery = true)
    Optional<Boolean> findActiveStatusByPlantIdAndBP(@Param("plantId") Long plantId, @Param("bpId") Long bpId);
}
