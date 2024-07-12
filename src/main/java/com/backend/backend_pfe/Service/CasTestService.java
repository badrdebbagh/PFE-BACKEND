package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.ProjectTestCaseCountsDTO;
import com.backend.backend_pfe.dto.TestProgressDTO;
import com.backend.backend_pfe.model.CasTest;

import java.util.List;
import java.util.Set;

public interface CasTestService {

    CasTest createCasTest(String titre, String description,  Long domaineId, Long fonctionnaliteId, Long projectId, Long cahierDeTestId);

    List<CasTest> getCasTests(Long domaineId, Long projectId, Long cahierDeTestId, Long fonctionnaliteId);

    List<CasTest> getCasTestsForUser(Long userId);

    List<CasTest> getTestCasesForUser(String username);

    List<CasTest> getAllTestCases();

    void executeTestCase(Long testCaseId);

    void assignTestCaseToUser(Long testCaseId, String username);

    long countTestCasesByProject(Long projectId);
    long countPassedTestsByProject(Long projectId);
    long countFailedTestsByProject(Long projectId);
    long countNotTestedCasesByProject(Long projectId);
    TestProgressDTO getTestProgress(Long projectId);

    List<ProjectTestCaseCountsDTO> getAllProjectsTestCaseCounts();
}
