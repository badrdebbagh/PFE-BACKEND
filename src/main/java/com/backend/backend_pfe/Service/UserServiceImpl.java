package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.model.ProjectAssignment;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.ProjectAssignmentRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProjectAssignmentRepository projectAssignmentRepository;


    @Autowired
    private final ProjectRepository projectRepository;

    public UserServiceImpl(UserRepository userRepository , PasswordEncoder passwordEncoder, ProjectAssignmentRepository projectAssignmentRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectAssignmentRepository = projectAssignmentRepository;

        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<List<UserModel>>getAllUsers() {
        try {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK) ;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>() , HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel) {
        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        UserModel newUser = userRepository.save(userModel);

return new ResponseEntity<>(newUser  , HttpStatus.CREATED);
    }

    @Override
    public UserModel findUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @Transactional
    @Override
    public UserModel assignProjectToEmployee(Long userId, Long projectId, USER_ROLE_PROJECTS role) {

        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Projet project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setUser(userModel);
        assignment.setProject(project);
        assignment.setRole(role);

        projectAssignmentRepository.save(assignment);
        System.out.println("Assigning role: " + role + " to user " + userModel.getEmail() + " for project " + project.getNom());


        return userModel;
    }


    public ResponseEntity<Void> suspendUser(Long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setSuspended(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> activateUser(Long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setSuspended(false);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}
