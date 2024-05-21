package com.backend.backend_pfe.dto;



import com.backend.backend_pfe.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectHierarchyDTO {
    private Projet project;
    private List<Domaine> domaines;
    private List<CahierDeTest> cahiers;
    private Map<Long, List<Fonctionnalité>> functionalities = new HashMap<>();
    private Map<Long, List<CasTest>> testCases = new HashMap<>();
    private Map<Long, List<TestCaseDescription>> steps = new HashMap<>();

    public void addFunctionalities(Long domaineId, List<Fonctionnalité> functionalities) {
        this.functionalities.put(domaineId, functionalities);
    }

    public void addTestCases(Long fonctionnaliteId, List<CasTest> testCases) {
        this.testCases.put(fonctionnaliteId, testCases);
    }

    public void addSteps(Long casTestId, List<TestCaseDescription> steps) {
        this.steps.put(casTestId, steps);
    }
}
