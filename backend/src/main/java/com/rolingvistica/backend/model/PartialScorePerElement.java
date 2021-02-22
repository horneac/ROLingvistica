package com.rolingvistica.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PartialScorePerElement {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false, foreignKey = @ForeignKey(name = "FK_partialscore_requirement"))
    private Requirement requirement;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String element;

    @Column(nullable = false)
    private Float score;

}
