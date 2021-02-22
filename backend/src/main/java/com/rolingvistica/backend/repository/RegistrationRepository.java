package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
