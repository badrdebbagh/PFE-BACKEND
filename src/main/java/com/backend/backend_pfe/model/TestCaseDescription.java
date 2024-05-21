package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "etapes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String resultatAttendu;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cas_test_id", nullable = true)
    @JsonBackReference
    private CasTest casTest;

    @OneToMany(mappedBy = "testCaseDescription", cascade = CascadeType.ALL, orphanRemoval = true  , fetch = FetchType.EAGER)
    private List<TestResult> testResults;
}