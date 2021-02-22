package com.rolingvistica.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Problem {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "contest_id", nullable = false, foreignKey = @ForeignKey(name = "FK_problem_contest"))
    private Contest contest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false, foreignKey = @ForeignKey(name = "FK_problem_section"))
    private Section section;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY)
    private List<Requirement> requirements = new ArrayList<>();

}
