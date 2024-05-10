package com.backend.backend_pfe.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private List<CasTest> casTests;



}
