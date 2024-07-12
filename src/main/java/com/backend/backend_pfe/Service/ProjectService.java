package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
import com.backend.backend_pfe.model.ProjectStatus;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface ProjectService {

    ResponseEntity<List<Projet>> getAllProjects();
    ResponseEntity<Projet> createProject(Projet projet);

    List<Projet> getAllProjectsByUserId(Long id);


    Projet createAndAssignProject(String projectName, String projectDescription, Long userId, USER_ROLE_PROJECTS role );

    Optional<ProjectDTO> getProjectWithCahier(Long id);

    CahierDeTestGlobal findCahierByProjectId(Long projectId);

    Projet assignDomaineToProject(Long projectId, Long domaineId);



    long countProjects();

    ProjectStatus updateProjectStatus(Long projectId);

    long countCompletedProjects();

    long countInProgressProjects();

    Map<String, Map<String, Map<String, Long>>> countTestCasesByDomainAndSubdomain(Long projectId);

    List<Projet> getInProgressProjects();
    List<Projet> getCompletedProjects();
}
