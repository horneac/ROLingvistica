package com.rolingvistica.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Requirement {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false, foreignKey = @ForeignKey(name = "FK_requirement_problem"))
    private Problem problem;

    @Column(nullable = false)
    private String correctAnswer;

    @Column(nullable = false)
    private String specification;
}
