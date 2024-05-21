package com.backend.backend_pfe.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "fonctionnalite")
public class Fonctionnalité {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    private String nom ;

    @OneToMany(mappedBy = "fonctionnalite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CasTest> casTests = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "domaine_id", nullable = true)
    private Domaine domaine;
@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projet projet;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cahier_de_test_id", nullable = true)
    private CahierDeTest cahierDeTest;

}
