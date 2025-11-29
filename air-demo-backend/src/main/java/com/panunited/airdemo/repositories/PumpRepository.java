package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Pump;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PumpRepository extends JpaRepository<Pump, Long> {
}
