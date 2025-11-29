package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
