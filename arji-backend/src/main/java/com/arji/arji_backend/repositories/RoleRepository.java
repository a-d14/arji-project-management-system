package com.arji.arji_backend.repositories;

import com.arji.arji_backend.models.Role;
import com.arji.arji_backend.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(UserRole userRole);
}
