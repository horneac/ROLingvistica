package com.rolingvistica.backend.dto;


import com.rolingvistica.backend.model.Requirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequirementDTO {

    private long id;
    private String specification;
    private String correctAnswer;

    public RequirementDTO(Requirement requirement) {
        this.id = requirement.getId();
        this.specification = requirement.getSpecification();
        this.correctAnswer = requirement.getCorrectAnswer();
    }

}
