package com.rolingvistica.backend.repository;

import com.rolingvistica.backend.model.Answer;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByRequirementId(long requirement_id);
}
