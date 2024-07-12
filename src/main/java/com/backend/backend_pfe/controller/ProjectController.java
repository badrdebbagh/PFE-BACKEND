package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.CasTestService;
import com.backend.backend_pfe.Service.ProjectService;

import com.backend.backend_pfe.Service.UserService;
import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.dto.ProjectRoleDTO;
import com.backend.backend_pfe.dto.ProjectTestCaseCountsDTO;
import com.backend.backend_pfe.dto.TestProgressDTO;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
import com.backend.backend_pfe.model.ProjectStatus;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.request.ProjetRequest;
import com.backend.backend_pfe.service2.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ProjectController {

private final ProjectService projectService;

private final UserService userService;

    @Autowired
    private UserDetailsServices userDetailsServices;

    @Autowired
    private CasTestService casTestService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }
    @GetMapping("/allProjects")
    public ResponseEntity<List<Projet>> getAllProjects() {
        return projectService.getAllProjects();
    }
    @PostMapping("createProject")
    public ResponseEntity<Projet> createProject(@RequestBody Projet projet){
        return projectService.createProject(projet);
    }


    @GetMapping("/userProjects")
    public ResponseEntity<Set<ProjectDTO>> getMyProjects() {
        Set<ProjectDTO> projectRoleDetails = userDetailsServices.getCurrentUserProjects(); // Assume this now returns ProjectRoleDTO
        return ResponseEntity.ok(projectRoleDetails);
    }

    @GetMapping("/userProjects2")
    public ResponseEntity<Set<ProjectDTO>> getProjects(){
        Set<ProjectDTO> userProjects = userDetailsServices.getUserProjects();
        return ResponseEntity.ok(userProjects);
    }

    @PostMapping("/createProjectWithDomaine")
    public ResponseEntity<Projet> createProjectWithDomaines(@RequestBody ProjetRequest projetRequest) {

        Projet createdProject = projectService.createAndAssignProject(
                projetRequest.getProjectName(),
                projetRequest.getDescription(),
                projetRequest.getUserId(),
                projetRequest.getRole()

        );
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Optional<ProjectDTO> projectDto = projectService.getProjectWithCahier(id);
        return projectDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/projects/{projectId}/cahier")
    public ResponseEntity<CahierDeTestGlobal> getCahierDeTestByProjectId(@PathVariable Long projectId) {
        CahierDeTestGlobal cahier = projectService.findCahierByProjectId(projectId);
        if (cahier != null) {
            return ResponseEntity.ok(cahier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{projectId}/assignDomaine")
    public ResponseEntity<Projet> assignDomaineToProject(
            @PathVariable Long projectId, @RequestParam Long domaineId) {

        Projet updatedProjet = projectService.assignDomaineToProject(projectId, domaineId);
        return ResponseEntity.ok(updatedProjet);
    }



    @GetMapping("/projects/count")
    public long countProjects() {
        return projectService.countProjects();
    }

    @GetMapping("/update-status")
    public ResponseEntity<ProjectStatus> updateProjectStatus(@RequestParam Long projectId) {
        ProjectStatus updatedStatus = projectService.updateProjectStatus(projectId);
        return ResponseEntity.ok(updatedStatus);
    }

    @GetMapping("projects/count/completed")
    public ResponseEntity<Map<String, Object>> getCompletedProjectsCount() {
        List<Projet> completedProjects = projectService.getCompletedProjects();
        long count = completedProjects.size();
        List<Map<String, String>> projectDetails = completedProjects.stream()
                .map(project -> {
                    Map<String, String> projectInfo = new HashMap<>();
                    projectInfo.put("name", project.getNom());
                    projectInfo.put("chefDeProjet", project.getChefDeProjet());
                    return projectInfo;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("projects", projectDetails);

        return ResponseEntity.ok(response);
    }

    @GetMapping("projects/count/in-progress")
    public ResponseEntity<Map<String, Object>> getInProgressProjectsCount() {
        List<Projet> inProgressProjects = projectService.getInProgressProjects();
        long count = inProgressProjects.size();
        List<Map<String, String>> projectDetails = inProgressProjects.stream()
                .map(project -> {
                    Map<String, String> projectInfo = new HashMap<>();
                    projectInfo.put("name", project.getNom());
                    projectInfo.put("chefDeProjet", project.getChefDeProjet());
                    return projectInfo;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("projects", projectDetails);

        return ResponseEntity.ok(response);
    }

    // count the number of testcases that are not tested yet
    @GetMapping("/projects/test-case-count")
    public ResponseEntity<Map<String, Map<String, Map<String, Long>>>> getTestCaseCountByDomainAndSubdomain(@RequestParam Long projectId) {
        Map<String, Map<String, Map<String, Long>>> result = projectService.countTestCasesByDomainAndSubdomain(projectId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{projectId}/test-cases/count")
    public ResponseEntity<Long> getTestCaseCount(@PathVariable Long projectId) {
        long count = casTestService.countTestCasesByProject(projectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{projectId}/test-cases/passed-count")
    public ResponseEntity<Long> getPassedTestCaseCount(@PathVariable Long projectId) {
        long count = casTestService.countPassedTestsByProject(projectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{projectId}/test-cases/failed-count")
    public ResponseEntity<Long> getFailedTestCaseCount(@PathVariable Long projectId) {
        long count = casTestService.countFailedTestsByProject(projectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{projectId}/test-cases/not-tested-count")
    public ResponseEntity<Long> getNotTestedTestCaseCount(@PathVariable Long projectId) {
        long count = casTestService.countNotTestedCasesByProject(projectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{projectId}/test-cases/progress")
    public ResponseEntity<TestProgressDTO> getTestProgress(@PathVariable Long projectId) {
        TestProgressDTO progress = casTestService.getTestProgress(projectId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/test-cases/counts")
    public ResponseEntity<List<ProjectTestCaseCountsDTO>> getAllProjectsTestCaseCounts() {
        List<ProjectTestCaseCountsDTO> counts = casTestService.getAllProjectsTestCaseCounts();
        return ResponseEntity.ok(counts);
    }



}
