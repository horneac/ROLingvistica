package com.rolingvistica.backend.service;

import com.rolingvistica.backend.model.Problem;
import com.rolingvistica.backend.repository.AnswerRepository;
import com.rolingvistica.backend.repository.ProblemRepository;
import com.rolingvistica.backend.repository.RequirementRepository;
import io.rolingvistica.dto.ContestProblemsDTO;
import io.rolingvistica.dto.ProblemDTO;
import io.rolingvistica.dto.SectionProblemsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService{
    private final ProblemRepository problemRepository;
    private final RequirementRepository requirementRepository;
    private final AnswerRepository answerRepository;


    public List<ProblemDTO> getAll() {
        return problemRepository.findAll().stream().map(this::getProblemDTO).collect(Collectors.toList());
    }

    public List<ContestProblemsDTO> listProblemsGroupedBySectionAndContest() {
        System.out.println("Sunt aici");

        List<ProblemDTO> problemDTOS = problemRepository.findAll().stream().map(this::getProblemDTO).collect(Collectors.toList());

        List<ContestProblemsDTO> contestProblemsDTOS = problemDTOS.stream()
                .collect(Collectors.groupingBy(ProblemDTO::getContestName))
                .entrySet()
                .stream()
                .map(contestStringListEntry -> {
                    ContestProblemsDTO contestProblemsDTO = new ContestProblemsDTO();
                    contestProblemsDTO.setName(contestStringListEntry.getKey());

                    List<SectionProblemsDTO> sectionProblemsDTOS = contestStringListEntry.getValue().stream()
                            .collect(Collectors.groupingBy(ProblemDTO::getSectionName))
                            .entrySet()
                            .stream()
                            .map(sectionStringListEntry -> {
                                SectionProblemsDTO sectionProblemsDTO = new SectionProblemsDTO();
                                sectionProblemsDTO.setName(sectionStringListEntry.getKey());
                                sectionProblemsDTO.setChildren(sectionStringListEntry.getValue());
                                return sectionProblemsDTO;
                            })
                            .collect(Collectors.toList());

                    contestProblemsDTO.setChildren(sectionProblemsDTOS);
                    return contestProblemsDTO;
                })
                .collect(Collectors.toList());

        return contestProblemsDTOS;
    }

    private ProblemDTO getProblemDTO(Problem problem) {
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setContestName(problem.getContest().getName());
        problemDTO.setId(problem.getId());
        problemDTO.setName(problem.getName());
        problemDTO.setSectionName(problem.getSection().getName());
        return problemDTO;
    }
}
