package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.TestPlanService;
import com.backend.backend_pfe.dto.TestIterationDTO;
import com.backend.backend_pfe.dto.TestPlanDTO;
import com.backend.backend_pfe.model.TestIteration;
import com.backend.backend_pfe.model.TestPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-plans")
public class TestPlanController {
    @Autowired
    private TestPlanService testPlanService;

    @PostMapping
    public ResponseEntity<TestPlan> createTestPlan(@RequestBody TestPlanDTO testPlanDTO) {
        TestPlan testPlan = testPlanService.createTestPlan(testPlanDTO);
        return new ResponseEntity<>(testPlan, HttpStatus.CREATED);
    }

    @GetMapping("/{testPlanId}")
    public ResponseEntity<TestPlan> getTestPlan(@PathVariable Long testPlanId) {
        TestPlan testPlan = testPlanService.getTestPlan(testPlanId);
        return ResponseEntity.ok(testPlan);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<TestPlan>> getAllTestPlansByProjectId(@PathVariable Long projectId) {
        List<TestPlan> testPlans = testPlanService.getAllTestPlansByProjectId(projectId);
        return ResponseEntity.ok(testPlans);
    }

    @PostMapping("/{testPlanId}/iterations")
    public ResponseEntity<TestIteration> createTestIteration(@PathVariable Long testPlanId, @RequestBody TestIterationDTO testIterationDTO) {
        TestIteration testIteration = testPlanService.createTestIteration(testPlanId, testIterationDTO);
        return new ResponseEntity<>(testIteration, HttpStatus.CREATED);
    }

    @GetMapping("/iterations/{testIterationId}")
    public ResponseEntity<TestIteration> getTestIteration(@PathVariable Long testIterationId) {
        TestIteration testIteration = testPlanService.getTestIteration(testIterationId);
        return ResponseEntity.ok(testIteration);
    }
}
