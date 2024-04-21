package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "projets")
@NoArgsConstructor
@AllArgsConstructor
public class Projet {
    public Projet(String nom) {
        this.nom = nom;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    @JsonIgnore
    @ManyToMany(mappedBy = "projets" , fetch = FetchType.LAZY)
    private Set<UserModel> utilisateurs = new HashSet<>();

    @ManyToMany(mappedBy = "projets")
    private Set<Domaine> domaines = new HashSet<>();

}

