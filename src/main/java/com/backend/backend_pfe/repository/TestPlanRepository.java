package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.TestPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface TestPlanRepository extends JpaRepository<TestPlan , Long> {
    List<TestPlan> findAllByProjetId(Long projectId);
}
