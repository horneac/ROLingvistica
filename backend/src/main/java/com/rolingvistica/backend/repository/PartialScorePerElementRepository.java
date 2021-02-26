package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.PartialScorePerElement;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartialScorePerElementRepository extends JpaRepository<PartialScorePerElement, Long> {

    List<PartialScorePerElement> findAllByRequirementId(long requirementId);
}
