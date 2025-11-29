package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
