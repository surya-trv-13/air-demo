package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Stone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoneRepository extends JpaRepository<Stone, Long> {
}
