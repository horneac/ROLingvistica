package com.rolingvistica.backend.dto;

import com.rolingvistica.backend.model.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProblemDTO implements Serializable {

    private Long id;
    private String name;
    private String contestName;
    private String sectionName;

    public ProblemDTO(Problem problem){
        this.id = problem.getId();
        this.name = problem.getName();
        this.contestName = problem.getContest().getName();
        this.sectionName = problem.getSection().getName();
    }
}
