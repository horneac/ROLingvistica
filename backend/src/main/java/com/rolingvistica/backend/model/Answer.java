package com.rolingvistica.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", foreignKey = @ForeignKey(name = "FK_answer_registration"))
    private Registration registration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", foreignKey = @ForeignKey(name = "FK_answer_requirement"))
    private Requirement requirement;

    @Column(nullable = false)
    private String providedAnswer;


}
