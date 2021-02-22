package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}
