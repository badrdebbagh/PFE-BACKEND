package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult , Long> {

    Optional<TestResult> findByTestCaseDescriptionId(Long testCaseDescriptionId);
    List<TestResult> findByCahierDeTestId(Long cahierDeTestId);

    List<TestResult> findByTestCaseDescriptionIdAndCahierDeTestId(Long testCaseDescriptionId, Long cahierDeTestId);


}
