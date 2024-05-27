package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/totalUsers")
    public ResponseEntity<Long> getTotalUsers() {
        long totalUsers = userService.countTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }
}
