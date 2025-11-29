package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.LocationPlantDistanceTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LocationPlantDistanceTimeRepository extends JpaRepository<LocationPlantDistanceTime, Long> {
    @Modifying
    @Query(value = """
			DELETE FROM LOCATION_PLANT_DISTANCE_TIME
			WHERE ORDER_ID = :orderId
			""", nativeQuery = true)
    void deleteAllByOrderId(Long orderId);

}
