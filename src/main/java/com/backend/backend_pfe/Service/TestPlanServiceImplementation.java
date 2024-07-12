package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.TestIterationDTO;
import com.backend.backend_pfe.dto.TestPlanDTO;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.TestIteration;
import com.backend.backend_pfe.model.TestPlan;
import com.backend.backend_pfe.repository.ProjectRepository;
import com.backend.backend_pfe.repository.TestIterationRepository;
import com.backend.backend_pfe.repository.TestPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class TestPlanServiceImplementation implements TestPlanService{
    @Autowired
    private TestPlanRepository testPlanRepository;

    @Autowired
    private TestIterationRepository testIterationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public TestPlan createTestPlan(TestPlanDTO testPlanDTO) {
        Projet projet = projectRepository.findById(testPlanDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        TestPlan testPlan = new TestPlan();
        testPlan.setName(testPlanDTO.getName());
        testPlan.setDescription(testPlanDTO.getDescription());
        testPlan.setProjet(projet);

        return testPlanRepository.save(testPlan);
    }

    @Override
    public TestPlan getTestPlan(Long testPlanId) {
        return testPlanRepository.findById(testPlanId)
                .orElseThrow(() -> new RuntimeException("Test plan not found"));
    }

    @Override
    public List<TestPlan> getAllTestPlansByProjectId(Long projectId) {
        return testPlanRepository.findAllByProjetId(projectId);
    }

    @Override
    public TestIteration createTestIteration(Long testPlanId, TestIterationDTO testIterationDTO) {
        TestPlan testPlan = testPlanRepository.findById(testPlanId)
                .orElseThrow(() -> new RuntimeException("Test plan not found"));

        TestIteration testIteration = new TestIteration();
        testIteration.setName(testIterationDTO.getName());
        testIteration.setStartDate(testIterationDTO.getStartDate());
        testIteration.setEndDate(testIterationDTO.getEndDate());
        testIteration.setTestPlan(testPlan);

        return testIterationRepository.save(testIteration);
    }

    @Override
    public TestIteration getTestIteration(Long testIterationId) {
        return testIterationRepository.findById(testIterationId)
                .orElseThrow(() -> new RuntimeException("Test iteration not found"));
    }

}
