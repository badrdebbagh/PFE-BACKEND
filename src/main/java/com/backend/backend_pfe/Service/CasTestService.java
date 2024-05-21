package com.backend.backend_pfe.Service;

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
}
