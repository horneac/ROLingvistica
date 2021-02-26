package com.rolingvistica.backend.service;

import com.rolingvistica.backend.model.PartialScorePerElement;
import com.rolingvistica.backend.repository.PartialScorePerElementRepository;
import io.rolingvistica.dto.PartialScoreElementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartialScoreElementService {
    private final PartialScorePerElementRepository partialScorePerElementRepository;

    public List<PartialScoreElementDTO> getPartialScoreElementsByRequirementId(long requirementId){
        return partialScorePerElementRepository.findAllByRequirementId(requirementId).stream().map(this::getPartialScoreElementDTO).collect(Collectors.toList());
    }


    private PartialScoreElementDTO getPartialScoreElementDTO(PartialScorePerElement element){
        PartialScoreElementDTO dto = new PartialScoreElementDTO();
        dto.setDescription(element.getDescription());
        dto.setElement(element.getElement());
        dto.setScore(element.getScore());

        return dto;
    }
}
