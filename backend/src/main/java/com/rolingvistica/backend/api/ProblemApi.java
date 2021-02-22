package com.rolingvistica.backend.api;

import com.rolingvistica.backend.dto.AnswerDTO;
import com.rolingvistica.backend.dto.ProblemDTO;
import com.rolingvistica.backend.dto.RequirementDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/problem")
public interface ProblemApi {

    @GetMapping("/")
    List<ProblemDTO> getAll();

    @GetMapping("/{problem_id}/requirements")
    List<RequirementDTO> getRequirements(@PathVariable("problem_id") long problem_id);

    @GetMapping("/requirement/{requirement_id}/answers")
    List<AnswerDTO> getAnswers(@PathVariable("requirement_id") long requirement_id);
}
