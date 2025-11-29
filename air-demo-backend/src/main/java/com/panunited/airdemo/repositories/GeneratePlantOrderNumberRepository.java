package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.GeneratePlantOrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface GeneratePlantOrderNumberRepository extends JpaRepository<GeneratePlantOrderNumber, Long> {
    @Query(value = """ 
            select TOP 1 g.* from Generate_Plant_Order_Number g  WITH (UPDLOCK, ROWLOCK) 
            where g.plant_id = :plantId and created_date = :orderDate  
           """, nativeQuery = true)
    Optional<GeneratePlantOrderNumber> findForUpdate(Long plantId, LocalDate orderDate);
}
