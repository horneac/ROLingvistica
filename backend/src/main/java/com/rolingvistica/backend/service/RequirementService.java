package com.rolingvistica.backend.service;

import com.rolingvistica.backend.model.Requirement;
import com.rolingvistica.backend.repository.RequirementRepository;
import io.rolingvistica.dto.RequirementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequirementService {
    private final RequirementRepository requirementRepository;

    public List<RequirementDTO> getRequirementsByProblemId(Long problemId) {
        return requirementRepository.findAllByProblemId(problemId).stream().map(this::getRequirementDTO).collect(Collectors.toList());
    }

    private RequirementDTO getRequirementDTO(Requirement requirement){
        RequirementDTO requirementDTO = new RequirementDTO();
        requirementDTO.setId(requirement.getId());
        requirementDTO.setCorrectAnswer(requirement.getCorrectAnswer());
        requirementDTO.setSpecification(requirement.getSpecification());

        return requirementDTO;
    }
}
