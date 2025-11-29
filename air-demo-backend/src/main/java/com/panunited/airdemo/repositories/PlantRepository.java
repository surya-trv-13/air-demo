package com.panunited.airdemo.repositories;

import com.panunited.airdemo.dto.PlantResponse;
import com.panunited.airdemo.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Query(value = """
			SELECT TOP 1 P.IS_ACTIVE
			FROM PLANT P
			WHERE P.ID = :plantId AND P.IS_DELETED = 0
			""", nativeQuery = true)
    Optional<Boolean> findActiveStatusByPlantId(Long plantId);

	@Query(value = """
			SELECT
				ID, PLANT_CODE, PLANT_NAME, PLANT_ADDRESS, IS_ACTIVE, PLANT_TELEPHONE, PLANT_LONGITUDE, PLANT_LATITUDE,
				GEO_RADIUS, NUMBER_OF_BP, ALLOW_NUM_TRUCK, WAITING_TIME_PER_BP_IN_SECOND,
				R.REGION_ID, R.REGION_CODE, R.REGION_NAME
			FROM PLANT P
			INNER JOIN REGION R
			ON P.REGION_ID = R.REGION_ID
			WHERE IS_ACTIVE = 1 AND IS_DELETED = 0
			ORDER BY PLANT_CODE
			""", nativeQuery = true)
	List<PlantResponse> findActivePlants();

	@Query(value = "SELECT * FROM PLANT WHERE IS_ACTIVE = 1 AND IS_DELETED = 0", nativeQuery = true)
	List<Plant> findAllList();
}
