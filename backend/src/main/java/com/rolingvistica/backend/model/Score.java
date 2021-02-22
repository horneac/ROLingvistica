package com.rolingvistica.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Score {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_score_answer"))
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", nullable = false, foreignKey = @ForeignKey(name = "FK_score_user"))
    private User evaluator;

    @Column(nullable = false)
    private Float score;
}
