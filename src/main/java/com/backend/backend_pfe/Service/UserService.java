package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.model.UserModel;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    ResponseEntity<List<UserModel>> getAllUsers();


    ResponseEntity<UserModel> createUser(UserModel userModel);

    UserModel findUser(Long userId);


    ResponseEntity<Void> deleteUser(Long id);

    UserModel assignProjectToEmployee(Long userId, Long projectId);

    ResponseEntity<Void> suspendUser(Long id);



}
