package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    List<Requirement> findAllByProblemId(long problem_id);
}
