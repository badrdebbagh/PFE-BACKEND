package com.backend.backend_pfe.Service;


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



}
