package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.ProjectService;

import com.backend.backend_pfe.Service.UserService;
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
@GetMapping("allProjects")
    public ResponseEntity<List<Projet>> getAllProjects(){
        return projectService.getAllProjects();
    }
    @PostMapping("createProject")
    public ResponseEntity<Projet> createProject(@RequestBody Projet projet){
        return projectService.createProject(projet);
    }


    @GetMapping("userProjects")
    public ResponseEntity<Set<Projet>> getMyProjects() {
        Set<Projet> projets = userDetailsServices.getCurrentUserProjects();
        return ResponseEntity.ok(projets);
    }

    @PostMapping("/createProjectUser")
    public ResponseEntity<Projet> createUserWithProject(@RequestBody ProjetRequest projetRequest) {
        try {
            Projet project = projectService.createAndAssignProject(
                    projetRequest.getNom(),
                    projetRequest.getDescription(),
                    projetRequest.getUserId());
            return ResponseEntity.ok(project);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
