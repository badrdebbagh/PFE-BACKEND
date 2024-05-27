package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.TestResultDTO;
import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.TestCaseDescription;
import com.backend.backend_pfe.model.TestResult;
import com.backend.backend_pfe.repository.CahierDeTestRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import com.backend.backend_pfe.repository.TestCaseDescriptionRepository;
import com.backend.backend_pfe.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestResultServiceImplementation implements TestResultService {

        @Autowired
        private TestCaseDescriptionRepository testCaseDescriptionRepository;

        @Autowired
        private CahierDeTestRepository cahierDeTestRepository;

        @Autowired
        private TestResultRepository testResultRepository;

        @Autowired
        private ProjectRepository projectRepository;
        public void saveTestResult(TestResultDTO testResultDTO) {
            TestResult testResult = new TestResult();
            testResult.setStatus(testResultDTO.getStatus());
            testResult.setComment(testResultDTO.getComment());

            TestCaseDescription testCaseDescription = testCaseDescriptionRepository
                    .findById(testResultDTO.getTestCaseDescriptionId())
                    .orElseThrow(() -> new RuntimeException("Test case description not found"));

            CahierDeTest cahierDeTest = cahierDeTestRepository
                    .findById(testResultDTO.getCahierDeTestId())
                    .orElseThrow(() -> new RuntimeException("Cahier de test not found"));

            testResult.setTestCaseDescription(testCaseDescription);

            testResult.setCahierDeTest(cahierDeTest);

            testResultRepository.save(testResult);

            Projet projet = cahierDeTest.getProjet();

            projet.updateStatus();
            projectRepository.save(projet);
        }

//    @Override
//    public Optional<TestResultDTO> getTestResultByDescriptionId(Long descriptionId) {
//        Optional<TestResult> optionalTestResult = testResultRepository.findByTestCaseDescriptionId(descriptionId);
//        return optionalTestResult.map(testResult -> new TestResultDTO(
//                testResult.getStatus(),
//                testResult.getTestCaseDescription().getId(),
//                testResult.getComment()
//        ));
//    }

    @Override
    public List<TestResultDTO> getTestResultsByCahierDeTestId(Long cahierDeTestId) {
        List<TestResult> testResults = testResultRepository.findByCahierDeTestId(cahierDeTestId);
        return testResults.stream()
                .map(testResult -> new TestResultDTO(
                        testResult.getId(),
                        testResult.getStatus(),
                        testResult.getTestCaseDescription().getId(),
                        testResult.getComment(),
                        testResult.getCahierDeTest().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TestResultDTO> getTestResultsByDescriptionAndCahier(Long testCaseDescriptionId, Long cahierDeTestId) {
        List<TestResult> testResults = testResultRepository.findByTestCaseDescriptionIdAndCahierDeTestId(testCaseDescriptionId, cahierDeTestId);
        return testResults.stream()
                .map(testResult -> new TestResultDTO(
                        testResult.getId(),
                        testResult.getStatus(),
                        testResult.getTestCaseDescription().getId(),
                        testResult.getComment(),

                        testResult.getCahierDeTest().getId()
                ))
                .collect(Collectors.toList());
    }

}
