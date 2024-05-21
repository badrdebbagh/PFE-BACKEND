package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.ProjectAssignmentService;
import com.backend.backend_pfe.model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/")
public class ProjectAssignmentController {

    @Autowired
    private ProjectAssignmentService projectAssignmentService;




}