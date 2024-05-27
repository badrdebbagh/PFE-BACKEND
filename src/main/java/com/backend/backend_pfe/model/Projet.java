package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;



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

    @JsonIgnore
    @OneToMany(mappedBy = "projet")
    private List<Fonctionnalité> fonctionnalités;

    @Transient
    @JsonProperty("chefDeProjet") // Include this property in JSON serialization
    public String getChefDeProjet() {
        return projectAssignments.stream()
                .filter(assignment -> assignment.getRole() == USER_ROLE_PROJECTS.CHEF_DE_PROJECT)
                .findFirst()
                .map(assignment -> assignment.getUser().getFullName())
                .orElse(null); // Returns null if no chef de projet is found.
    }

    public void updateStatus() {
        System.out.println("Updating status for project: " + this.nom);

        boolean allTestsOK = true; // Flag to check if all tests are OK

        // Check if the list of domains is not null
        if (domaines != null) {
            // Iterate over each domain in the list
            for (Domaine domaine : domaines) {
                // Iterate over each CahierDeTest in the domain's cahiers
                for (CahierDeTest cahierDeTest : domaine.getCahiers()) {
                    // Iterate over each Fonctionnalité in the CahierDeTest's fonctionnalités
                    for (Fonctionnalité fonctionnalité : cahierDeTest.getFonctionnalites()) {
                        // Iterate over each CasTest in the Fonctionnalité's casTests
                        for (CasTest casTest : fonctionnalité.getCasTests()) {
                            // Iterate over each TestCaseDescription in the CasTest's testCaseDescriptions
                            for (TestCaseDescription testCaseDescription : casTest.getTestCaseDescriptions()) {
                                // Iterate over each TestResult in the TestCaseDescription's testResults
                                for (TestResult testResult : testCaseDescription.getTestResults()) {
                                    System.out.println("Checking test result: " + testResult.getId() + " with status: " + testResult.getStatus());
                                    // Check if the status of the TestResult is not "OK"
                                    if (!"OK".equalsIgnoreCase(testResult.getStatus())) {
                                        allTestsOK = false;
                                        // If any TestResult status is not "OK", set the project status to EN_PROGRES
                                        this.status = ProjectStatus.EN_PROGRES;
                                        System.out.println("Project status set to EN_PROGRES due to test result: " + testResult.getId());
                                        return; // Exit the method early as the project is not complete
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // If all test results are "OK", set the project status to ACHEVEE
        if (allTestsOK) {
            this.status = ProjectStatus.ACHEVEE;
            System.out.println("Project status set to ACHEVEE");
        }
    }





}

