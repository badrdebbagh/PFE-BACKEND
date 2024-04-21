package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.UserService;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5001")
@RestController
@RequestMapping("/api")

public class UserController {

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("allUsers")
    public ResponseEntity<List<UserModel>> getAllUsers(){
       return userService.getAllUsers();
    }

    @PostMapping("createUser")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel){
        return userService.createUser(userModel);
    }

    @GetMapping("/allUsers/{userId}")
    public UserModel findUser(@PathVariable Long userId){
        return userService.findUser(userId);
    }

    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/user/{userId}/project/{projectId}")
    public UserModel assignProjectToEmployee(@PathVariable Long userId , @PathVariable Long projectId){
return userService.assignProjectToEmployee(userId , projectId);
    }

    @PutMapping("/suspendUser/{id}")
    public ResponseEntity<Void> suspendUser(@PathVariable Long id){
        return userService.suspendUser(id);
    }



}
