package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.dto.UserProjectDTO;
import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import com.backend.backend_pfe.model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    ResponseEntity<List<UserModel>> getAllUsers();


    ResponseEntity<UserModel> createUser(UserModel userModel);

    UserModel findUser(Long userId);


    ResponseEntity<Void> deleteUser(Long id);

//    UserModel assignProjectToEmployee(Long userId, Long projectId);

    UserModel assignProjectToEmployee(Long userId, Long projectId, USER_ROLE_PROJECTS role);

    ResponseEntity<Void> suspendUser(Long id);


    ResponseEntity<Void> activateUser(Long id);

    UserModel assignDomaineToUser(Long userId, Long domaineId);

    UserModel assignUserToDomaineAndProject(Long userId, Long domaineId, Long projectId);

    List<CasTest> getTestCasesForUser(Long userId);

    List<CasTest> getTestCasesForUserAndProject(Long userId, Long projectId);

    UserProjectDTO getUserProjectsData(Long userId);

    long countTotalUsers();
}
