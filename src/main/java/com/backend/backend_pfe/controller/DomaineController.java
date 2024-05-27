package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.DomaineService;
import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.SousDomaine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domaines")
public class DomaineController {

    private final DomaineService domaineService;

    @Autowired
    public DomaineController(DomaineService domaineService) {
        this.domaineService = domaineService;
    }

    @GetMapping
    public ResponseEntity<List<Domaine>> getAllDomaines() {
        List<Domaine> domaines = domaineService.getAllDomaines();
        return ResponseEntity.ok(domaines);
    }

    @PostMapping("/create")
    public ResponseEntity<Domaine> createDomaine(@RequestBody Domaine domaine){
        Domaine newDomaine = domaineService.createDomaine(domaine);
        return ResponseEntity.ok(newDomaine);
    }
}
