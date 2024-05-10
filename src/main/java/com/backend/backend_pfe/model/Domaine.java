package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domaines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Domaine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @JsonIgnore
    @OneToMany(mappedBy = "domaine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CahierDeTest> cahiers = new HashSet<>();

}
