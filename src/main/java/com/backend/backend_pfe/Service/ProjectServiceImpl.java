package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.ProjectRepository;
import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository ;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Projet>>getAllProjects() {
        try {
            return new ResponseEntity<>(projectRepository.findAll(), HttpStatus.OK) ;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>() , HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Projet> createProject(Projet projet) {
        Projet newProject = projectRepository.save(projet);
        return new ResponseEntity<>(newProject  , HttpStatus.CREATED);
    }

    @Override
    public List<Projet> getAllProjectsByUserId(Long userId) {
        return projectRepository.findByUtilisateurs_Id(userId);
    }

    public Projet createAndAssignProject(String projectName, String projectDescription, Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Projet newProject = new Projet();
        newProject.setNom(projectName);
        newProject.setDescription(projectDescription);

        // Associate project with user
        user.getProjets().add(newProject);
        newProject.getUtilisateurs().add(user);

        // Save the project, which also updates the user due to CascadeType.ALL in the mapping
        return projectRepository.save(newProject);
    }

}
