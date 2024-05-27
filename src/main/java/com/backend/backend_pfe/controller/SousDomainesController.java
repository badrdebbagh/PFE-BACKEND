package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.SousDomaineService;
import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.SousDomaine;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sousDomaines")
public class SousDomainesController {

    private final SousDomaineService sousDomaineService;
@Autowired
    public SousDomainesController(SousDomaineService sousDomaineService) {
        this.sousDomaineService = sousDomaineService;
    }

    @GetMapping
    public ResponseEntity<List<SousDomaine>> getAllDomaines() {
        List<SousDomaine> sousDomaines = sousDomaineService.getAllDomaines();
        return ResponseEntity.ok(sousDomaines);
    }

    @PostMapping("/create")
    public ResponseEntity<SousDomaine> createSousDomaine(@RequestBody SousDomaine sousDomaine){
    SousDomaine newSousDomaine = sousDomaineService.createSousDomaine(sousDomaine);
    return ResponseEntity.ok(newSousDomaine);
    }
}
