package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.TestIteration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface TestIterationRepository extends JpaRepository<TestIteration , Long> {

    List<TestIteration> findAllByTestPlanId(Long testPlanId);
}
