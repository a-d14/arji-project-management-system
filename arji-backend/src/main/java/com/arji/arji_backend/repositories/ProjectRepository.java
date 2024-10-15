package com.arji.arji_backend.repositories;

import com.arji.arji_backend.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
