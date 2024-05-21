package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.TestCaseDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestCaseDescriptionRepository extends JpaRepository<TestCaseDescription , Long> {
    List<TestCaseDescription> findByCasTestId(Long casTestId);
    List<TestCaseDescription> findByCasTest_IdIn(List<Long> casTestIds);

}
