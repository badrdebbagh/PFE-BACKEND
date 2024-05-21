package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.TestCaseDescriptionService;
import com.backend.backend_pfe.model.TestCaseDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestCaseDescriptionController {

    private final TestCaseDescriptionService testCaseDescriptionService;

    @Autowired
    public TestCaseDescriptionController( TestCaseDescriptionService testCaseDescriptionService) {
        this.testCaseDescriptionService = testCaseDescriptionService;

    }

    @GetMapping("/etape/{testCaseId}")
    public ResponseEntity<List<TestCaseDescription>> getDescriptionByTestCaseId(@PathVariable Long testCaseId) {
        List<TestCaseDescription> descriptions = testCaseDescriptionService.findDescriptionByCasTestId(testCaseId);
        if (descriptions.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(descriptions);
        }
    }
    @PostMapping("/testcases/{casTestId}")
    public ResponseEntity<TestCaseDescription> createOrUpdateDescription(
            @PathVariable Long casTestId,
            @RequestBody TestCaseDescription description) {
        TestCaseDescription savedDescription = testCaseDescriptionService.saveDescriptionForTestCase(description, casTestId);
        return ResponseEntity.ok(savedDescription);
    }


}
