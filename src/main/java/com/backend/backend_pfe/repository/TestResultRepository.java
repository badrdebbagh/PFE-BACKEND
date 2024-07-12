package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult , Long> {

    Optional<TestResult> findByTestCaseDescriptionId(Long testCaseDescriptionId);
    List<TestResult> findByCahierDeTestId(Long cahierDeTestId);

    List<TestResult> findByTestCaseDescriptionIdAndCahierDeTestId(Long testCaseDescriptionId, Long cahierDeTestId);

    @Query("SELECT COUNT(tr) FROM TestResult tr WHERE tr.status = 'OK' AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.projet.id = :projectId AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.domaine.id = :domaineId AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.sousDomaine.id = :sousDomaineId")
    long countOkResultsByProjectDomaineAndSousDomaine(@Param("projectId") Long projectId, @Param("domaineId") Long domaineId, @Param("sousDomaineId") Long sousDomaineId);

    @Query("SELECT COUNT(tr) FROM TestResult tr WHERE tr.status = 'KO' AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.projet.id = :projectId AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.domaine.id = :domaineId AND tr.testCaseDescription.casTest.fonctionnalite.cahierDeTest.sousDomaine.id = :sousDomaineId")
    long countKoResultsByProjectDomaineAndSousDomaine(@Param("projectId") Long projectId, @Param("domaineId") Long domaineId, @Param("sousDomaineId") Long sousDomaineId);

    List<TestResult> findByTestCaseDescriptionIdIn(List<Long> testCaseDescriptionIds);

    @Query("SELECT COUNT(tr) FROM TestResult tr WHERE tr.testCaseDescription.casTest.projet.id = :projectId AND tr.status = 'OK'")
    long countPassedTestsByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(tr) FROM TestResult tr WHERE tr.testCaseDescription.casTest.projet.id = :projectId AND tr.status = 'KO'")
    long countFailedTestsByProjectId(@Param("projectId") Long projectId);


}
