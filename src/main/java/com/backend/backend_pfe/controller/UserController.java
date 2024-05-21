package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.UserService;
import com.backend.backend_pfe.dto.UserProjectDTO;
import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<?> assignProjectToEmployee(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody USER_ROLE_PROJECTS role) {
        try {


            UserModel userModel = userService.assignProjectToEmployee(userId, projectId, role);
            return ResponseEntity.ok(userModel);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/suspendUser/{id}")
    public ResponseEntity<Void> suspendUser(@PathVariable Long id){
        return userService.suspendUser(id);
    }

    @PutMapping("/activateUser/{id}")
    public ResponseEntity<Void> activateUser(@PathVariable Long id){
        return userService.activateUser(id);
    }


    @PostMapping("/{userId}/domaines/{domaineId}")
    public ResponseEntity<UserModel> assignDomaineToUser(@PathVariable Long userId, @PathVariable Long domaineId) {
        UserModel updatedUser = userService.assignDomaineToUser(userId, domaineId);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/domaines/{domaineId}/projects/{projectId}")
    public ResponseEntity<UserModel> assignUserToDomaineAndProject(@PathVariable Long userId, @PathVariable Long domaineId, @PathVariable Long projectId) {
        UserModel updatedUser = userService.assignUserToDomaineAndProject(userId, domaineId, projectId);
        return ResponseEntity.ok(updatedUser);
    }

//    @GetMapping("/{userId}/testcases")
//    public ResponseEntity<List<CasTest>> getTestCasesForUser(@PathVariable Long userId) {
//        List<CasTest> testCases = userService.getTestCasesForUser(userId);
//        return ResponseEntity.ok(testCases);
//    }

    @GetMapping("/user/{userId}/projects/{projectId}/testcases")
    public ResponseEntity<List<CasTest>> getTestCasesForUserAndProject(@PathVariable Long userId, @PathVariable Long projectId) {
        List<CasTest> testCases = userService.getTestCasesForUserAndProject(userId, projectId);
        return ResponseEntity.ok(testCases);
    }

    @GetMapping("/user/{userId}/projects")
    public ResponseEntity<UserProjectDTO> getUserProjectsData(@PathVariable Long userId) {
        UserProjectDTO data = userService.getUserProjectsData(userId);
        return ResponseEntity.ok(data);
    }




}
