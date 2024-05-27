package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.TestResultDTO;

import java.util.List;
import java.util.Optional;

public interface TestResultService {
    void saveTestResult(TestResultDTO testResultDTO);

//    Optional<TestResultDTO> getTestResultByDescriptionId(Long descriptionId);

    List<TestResultDTO> getTestResultsByCahierDeTestId(Long cahierDeTestId);

    List<TestResultDTO> getTestResultsByDescriptionAndCahier(Long testCaseDescriptionId, Long cahierDeTestId);
}
