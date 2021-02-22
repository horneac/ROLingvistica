package com.rolingvistica.backend.service;

import com.rolingvistica.backend.api.ProblemApi;
import com.rolingvistica.backend.dto.AnswerDTO;
import com.rolingvistica.backend.dto.ProblemDTO;
import com.rolingvistica.backend.dto.RequirementDTO;
import com.rolingvistica.backend.repository.AnswerRepository;
import com.rolingvistica.backend.repository.ProblemRepository;
import com.rolingvistica.backend.repository.RequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProblemApiServiceImpl implements ProblemsAp {
    private final ProblemRepository problemRepository;
    private final RequirementRepository requirementRepository;
    private final AnswerRepository answerRepository;

    @Override
    public List<ProblemDTO> getAll() {
//            List<ProblemDTO> problemDTOS = problemRepository.findAll().stream().map(ProblemDTO::new).collect(Collectors.toList());
//            Map<String, Map<String, List<ProblemDTO>>> problemsGrouppedByContestAndSection = problemDTOS.stream()
//                    .collect(Collectors.groupingBy(ProblemDTO::getContestName, Collectors.groupingBy(ProblemDTO::getSectionName)));
//            return problemsGrouppedByContestAndSection;
        return problemRepository.findAll().stream().map(ProblemDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<RequirementDTO> getRequirements(long problem_id) {
        return requirementRepository.findAllByProblemId(problem_id).stream().map(RequirementDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<AnswerDTO> getAnswers(long requirement_id) {
        return answerRepository.findAllByRequirementId(requirement_id).stream().map(AnswerDTO::new).collect(Collectors.toList());
    }
}
