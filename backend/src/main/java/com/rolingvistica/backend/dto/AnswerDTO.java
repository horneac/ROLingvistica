package com.rolingvistica.backend.dto;

import com.rolingvistica.backend.model.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerDTO {

    private long id;
    private String providedAnswer;

    public AnswerDTO(Answer answer){
        this.id = answer.getId();
        this.providedAnswer = answer.getProvidedAnswer();
    }
}
