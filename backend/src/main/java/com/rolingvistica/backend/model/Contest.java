package com.rolingvistica.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Contest {

    @Column(unique = true, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "contest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();
}
