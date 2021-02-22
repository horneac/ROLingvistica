package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
