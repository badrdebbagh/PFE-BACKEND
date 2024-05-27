package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.CahierDeTestGlobalRepository;
import com.backend.backend_pfe.repository.DomaineRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository ;

    private final CahierDeTestGlobalRepository cahierDeTestGlobalRepository;

    private final DomaineRepository domaineRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, CahierDeTestGlobalRepository cahierDeTestGlobalRepository, DomaineRepository domaineRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.domaineRepository = domaineRepository;
    }



    @Override
    public ResponseEntity<Projet> createProject(Projet projet) {
        Projet newProject = projectRepository.save(projet);
        return new ResponseEntity<>(newProject  , HttpStatus.CREATED);
    }

//    @Override
//    public List<Projet> getAllProjectsByUserId(Long userId) {
//        return projectRepository.findByUtilisateurs_Id(userId);
//    }

    @Override
    public List<Projet> getAllProjectsByUserId(Long userId) {
        return projectRepository.findProjectsByUserId(userId);
    }
    @Transactional
    public Projet createAndAssignProject(String projectName, String projectDescription, Long userId, USER_ROLE_PROJECTS role) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Projet newProject = new Projet(projectName);
        newProject.setDescription(projectDescription);

        // Assign the domaines to the project
//        List<Domaine> domaines = domaineRepository.findAllById(domaineIds);
//        newProject.setDomaines(new HashSet<>(domaines));

        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setUser(user);
        assignment.setProject(newProject);
        assignment.setRole(role);

        user.getProjectAssignments().add(assignment);
        newProject.getProjectAssignments().add(assignment);

        projectRepository.saveAndFlush(newProject);
        return newProject;
    }

    @Override
    public Optional<ProjectDTO> getProjectWithCahier(Long id) {
        return projectRepository.findById(id).map(projet -> {
            ProjectDTO dto = new ProjectDTO();
            dto.setProjectId(projet.getId());
            dto.setProjectName(projet.getNom());
            dto.setDescription(projet.getDescription());
            if (projet.getCahierDeTestGlobal() != null) {
                dto.setCahierDeTestGlobalId(projet.getCahierDeTestGlobal().getId());
                dto.setCahierDeTestGlobalNom(projet.getCahierDeTestGlobal().getNom());
            }
            return dto;
        });
    }

    @Override
    public CahierDeTestGlobal findCahierByProjectId(Long projectId) {
        return cahierDeTestGlobalRepository.findByProjectId(projectId);
    }

    @Override
    public Projet assignDomaineToProject(Long projectId, Long domaineId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Domaine domaine = domaineRepository.findById(domaineId)
                .orElseThrow(() -> new RuntimeException("Domain not found"));

        projet.getDomaines().add(domaine);
        return projectRepository.save(projet);
    }

    @Override


    public Projet updateProjectStatus(Long projectId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projet.updateStatus();
        return projectRepository.save(projet);
    }


    @Override
    public ResponseEntity<List<Projet>> getAllProjects() {
        try {
            List<Projet> projects = projectRepository.findAllWithDomaines();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


}
