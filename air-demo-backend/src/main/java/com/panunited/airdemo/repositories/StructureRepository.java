package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureRepository extends JpaRepository<Structure, Long> {
}
