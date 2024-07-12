package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "test_iterations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestIteration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_plan_id")
    private TestPlan testPlan;

    @OneToMany(mappedBy = "testIteration", cascade = CascadeType.ALL)
    private List<TestResult> testResults;
}
