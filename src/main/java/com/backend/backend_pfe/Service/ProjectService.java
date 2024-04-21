package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.model.Projet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    ResponseEntity<List<Projet>> getAllProjects();

    ResponseEntity<Projet> createProject(Projet projet);

    List<Projet> getAllProjectsByUserId(Long id);

    Projet createAndAssignProject(String name, String description, Long userId);
}
