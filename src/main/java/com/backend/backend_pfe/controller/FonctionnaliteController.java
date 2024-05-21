package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.FonctionnaliteService;
import com.backend.backend_pfe.model.Fonctionnalité;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class FonctionnaliteController {

    @Autowired
    private FonctionnaliteService fonctionnaliteService;

    @GetMapping("/functionalities")
    public List<Fonctionnalité> getFunctionalities(@RequestParam Long domaineId , @RequestParam Long projectId , @RequestParam Long cahierDeTestId ) {
        return fonctionnaliteService.getFunctionalitiesByProjectAndDomainAndCahierDeTest(domaineId , projectId ,  cahierDeTestId);
    }

    @PostMapping("/createFunctionnality")
    public ResponseEntity<Fonctionnalité> createFonctionnalité(@RequestParam String nom, @RequestParam Long domaineId , @RequestParam Long projectId ,@RequestParam Long cahierDeTestId ) {
        Fonctionnalité fonctionnalité = fonctionnaliteService.createFonctionnalité(nom, domaineId , projectId , cahierDeTestId);
        return ResponseEntity.ok(fonctionnalité);
    }
}
