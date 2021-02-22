package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
