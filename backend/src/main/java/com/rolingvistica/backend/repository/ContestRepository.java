package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
