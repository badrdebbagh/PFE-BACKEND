package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.TestIterationDTO;
import com.backend.backend_pfe.dto.TestPlanDTO;
import com.backend.backend_pfe.model.TestIteration;
import com.backend.backend_pfe.model.TestPlan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface TestPlanService {
    TestPlan createTestPlan(TestPlanDTO testPlanDTO);
    TestPlan getTestPlan(Long testPlanId);
    List<TestPlan> getAllTestPlansByProjectId(Long projectId);
    TestIteration createTestIteration(Long testPlanId, TestIterationDTO testIterationDTO);
    TestIteration getTestIteration(Long testIterationId);
}
