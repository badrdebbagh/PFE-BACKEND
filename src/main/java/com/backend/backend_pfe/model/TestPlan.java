package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "test_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "testPlan", cascade = CascadeType.ALL)
    private List<TestIteration> iterations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Projet projet;
}
