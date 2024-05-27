package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.ProjectService;

import com.backend.backend_pfe.Service.UserService;
import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.dto.ProjectRoleDTO;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class ProjectController {

private final ProjectService projectService;

private final UserService userService;

    @Autowired
    private UserDetailsServices userDetailsServices;

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

    @GetMapping("/{id}/status")
    public ResponseEntity<Projet> getProjectStatus(@PathVariable Long id) {
        Projet projet = projectService.updateProjectStatus(id);
        return ResponseEntity.ok(projet);
    }



}
