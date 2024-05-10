package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.SousDomaineService;
import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.SousDomaine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
