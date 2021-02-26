package com.rolingvistica.backend.service;

import com.rolingvistica.backend.model.Answer;
import com.rolingvistica.backend.repository.AnswerRepository;
import io.rolingvistica.dto.AnswerDTO;
import io.rolingvistica.dto.GroupedAnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public List<AnswerDTO> getAnswers(long requirementId) {
        return answerRepository.findAllByRequirementId(requirementId).stream().map(this::getAnswerDTO).collect(Collectors.toList());
    }

    public List<GroupedAnswerDTO> getAnswersGroupedByProvidedAnswer(long requirementId){
        List<Answer> answers = answerRepository.findAllByRequirementId(requirementId);

        List<GroupedAnswerDTO> groupedAnswerDTOS = answers.stream()
                .collect(Collectors.groupingBy(Answer::getProvidedAnswer))
                .entrySet()
                .stream()
                .map( stringListEntry -> {
                    GroupedAnswerDTO groupedAnswerDTO = new GroupedAnswerDTO();
                    groupedAnswerDTO.setNrOfAnswers(stringListEntry.getValue().size());
                    groupedAnswerDTO.setProvidedAnswer(stringListEntry.getKey());
                    groupedAnswerDTO.setRequirementId(requirementId);

                    return groupedAnswerDTO;
                })
                .collect(Collectors.toList());

        return groupedAnswerDTOS;
    }

    private AnswerDTO getAnswerDTO(Answer answer){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setProvidedAnswer(answer.getProvidedAnswer());
        answerDTO.setRequirementId(answer.getRequirement().getId());

        return answerDTO;
    }
}
