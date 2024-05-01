package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.CahierDeTestGlobalService;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CahierDeTestGlobalController {

    @Autowired
    private CahierDeTestGlobalService cahierDeTestGlobalService;

    @PostMapping("/create/{projectId}") // creer un a cahier de test global en le liant avec son projet
    public ResponseEntity<CahierDeTestGlobal> createGlobalCahierDeTest(@PathVariable Long projectId){
         CahierDeTestGlobal cahierDeTestGlobal = cahierDeTestGlobalService.createGlobalCahierDeTest(projectId);
         return ResponseEntity.ok(cahierDeTestGlobal);
    }

    @GetMapping("/cahierdetestglobal/{id}")
    public CahierDeTestGlobal findCahierDeTestGlobal(@PathVariable Long id){
        return cahierDeTestGlobalService.findCahierDeTestGlobal(id);

    }

}
