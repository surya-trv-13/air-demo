package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.PlantAdditionalOffTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlantAdditionalOffTimeRepository extends JpaRepository<PlantAdditionalOffTime, Long> {
    @Query(value = "SELECT po.* FROM plant_additional_off_time po WHERE plant_id=:plantId", nativeQuery = true)
    List<PlantAdditionalOffTime> getByPlantId(long plantId);
}
