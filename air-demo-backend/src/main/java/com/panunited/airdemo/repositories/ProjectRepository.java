package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
