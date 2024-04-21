package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domaines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domaine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;


    @ManyToMany
    @JoinTable(
            name = "projet_domaine",
            joinColumns = @JoinColumn(name = "domaine_id"),
            inverseJoinColumns = @JoinColumn(name = "projet_id")
    )
    private Set<Projet> projets = new HashSet<>();
}
