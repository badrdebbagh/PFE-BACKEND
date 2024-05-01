package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {



    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return roleService.getAllRoles();
    }

@GetMapping("/userRoles")
    public List<String> getAllUserRoles(){return roleService.getAllUserRoles();}
}
