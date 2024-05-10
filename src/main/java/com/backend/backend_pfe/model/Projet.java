package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();


    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CahierDeTestGlobal cahierDeTestGlobal;

    @ManyToMany
    @JoinTable(
            name = "projet_domaine",
            joinColumns = @JoinColumn(name = "projet_id"),
            inverseJoinColumns = @JoinColumn(name = "domaine_id")
    )
    private Set<Domaine> domaines = new HashSet<>();

    @Transient
    @JsonProperty("chefDeProjet") // Include this property in JSON serialization
    public String getChefDeProjet() {
        return projectAssignments.stream()
                .filter(assignment -> assignment.getRole() == USER_ROLE_PROJECTS.CHEF_DE_PROJECT)
                .findFirst()
                .map(assignment -> assignment.getUser().getFullName())
                .orElse(null); // Returns null if no chef de projet is found.
    }

}

