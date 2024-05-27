package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // OK or KO
//    private Double executionTime;
//    private LocalDateTime runDate;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "test_case_description_id", nullable = true)
    @JsonIgnore
    private TestCaseDescription testCaseDescription;

    @ManyToOne
    @JoinColumn(name = "cahier_de_test_id", nullable = true)
    @JsonIgnore
    private CahierDeTest cahierDeTest;
}

