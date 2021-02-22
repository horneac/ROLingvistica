package com.rolingvistica.backend.service;

import com.rolingvistica.backend.model.Answer;
import com.rolingvistica.backend.repository.AnswerRepository;
import io.rolingvistica.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<AnswerDTO> getAnswers(long requirement_id) {
        return answerRepository.findAllByRequirementId(requirement_id).stream().map(this::getAnswerDTO).collect(Collectors.toList());
    }

    private AnswerDTO getAnswerDTO(Answer answer){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setProvidedAnswer(answer.getProvidedAnswer());
        answerDTO.setRequirementId(answer.getRequirement().getId());

        return answerDTO;
    }
}
