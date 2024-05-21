package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cas_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    private String Status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "domaine_id", nullable = true)
    private Domaine domaine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fonctionnalite_id", nullable = true)
    @JsonIgnore
    private Fonctionnalité fonctionnalite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Projet projet;

    @OneToMany(mappedBy = "casTest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TestCaseDescription> testCaseDescriptions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "sous_cahier_de_test_id", nullable = true)
    private CahierDeTest cahierDeTest;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel userModel;


}
