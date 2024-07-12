package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.TestResultService;
import com.backend.backend_pfe.dto.TestResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @PostMapping("/test-results")
    public ResponseEntity<?> submitTestResult(@RequestBody TestResultDTO testResultDTO) {
        try {
            testResultService.saveTestResult(testResultDTO);
            return ResponseEntity.ok("Test result submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting test result: " + e.getMessage());
        }
    }

    @GetMapping("/test-results")
    public ResponseEntity<List<TestResultDTO>> getTestResults(
            @RequestParam(required = false) Long testCaseDescriptionId,
            @RequestParam(required = false) Long cahierDeTestId) {
        try {
            List<TestResultDTO> testResultDTOs;
            if (testCaseDescriptionId != null && cahierDeTestId != null) {
                testResultDTOs = testResultService.getTestResultsByDescriptionAndCahier(testCaseDescriptionId, cahierDeTestId);
            } else if (cahierDeTestId != null) {
                testResultDTOs = testResultService.getTestResultsByCahierDeTestId(cahierDeTestId);
            } else {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(testResultDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("test-results/count/ok")
    public long countOkResults(@RequestParam Long projectId, @RequestParam Long domaineId, @RequestParam Long sousDomaineId) {
        return testResultService.countOkResultsByProjectDomaineAndSousDomaine(projectId, domaineId, sousDomaineId);
    }

    @GetMapping("test-results/count/ko")
    public long countKoResults(@RequestParam Long projectId, @RequestParam Long domaineId, @RequestParam Long sousDomaineId) {
        return testResultService.countKoResultsByProjectDomaineAndSousDomaine(projectId, domaineId, sousDomaineId);
    }

}
