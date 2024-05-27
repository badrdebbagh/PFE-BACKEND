package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.CahierDeTestService;
import com.backend.backend_pfe.dto.CahierDeTestDTO;
import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.Fonctionnalité;
import com.backend.backend_pfe.model.Projet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CahierDeTestController {

    @Autowired
    private CahierDeTestService cahierDeTestService;

    private static final Logger logger = LoggerFactory.getLogger(CahierDeTestController.class);
    @GetMapping("cahiersDeTest") // fetch all cahiers de tests
    public ResponseEntity<List<CahierDeTest>> getAllCahiersDeTest() {
        List<CahierDeTest> cahiers = cahierDeTestService.getAllCahiersDeTest();
        if (cahiers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cahiers);
    }

//    @PostMapping("/create/{cahierDeTestGlobalId}") // creer et lier un sous cahier de test a un cahier de test global
//    public ResponseEntity<CahierDeTest> createSubCahierDeTest(@PathVariable Long cahierDeTestGlobalId, @RequestBody CahierDeTest cahierDeTest) {
//        CahierDeTest newCahierDeTest = cahierDeTestService.createSubCahierDeTest(cahierDeTestGlobalId, cahierDeTest);
//        return ResponseEntity.ok(newCahierDeTest);
//
//    }

    @PostMapping("/createSousCahierDeTest")
    public ResponseEntity<CahierDeTest> createSubCahierDeTest(@RequestBody CahierDeTestDTO cahierDeTestDTO) {

        CahierDeTest newCahierDeTest = cahierDeTestService.createSubCahierDeTest(cahierDeTestDTO);
        return ResponseEntity.ok(newCahierDeTest);
    }
    @PostMapping("/setDomain/{cahierId}") // attribuer un domaine et un sous domaine au cahier de test selectionne
    public ResponseEntity<CahierDeTest> setDomainAndSubDomain(@PathVariable Long cahierId, @RequestParam Long domaineId, @RequestParam(required = false) Long subDomaineId) {
        CahierDeTest updatedCahier = cahierDeTestService.setDomainAndSubDomain(cahierId, domaineId, subDomaineId);
        return ResponseEntity.ok(updatedCahier);

    }


    @GetMapping("/cahierDeTestGlobal/{cahierDeTestGlobalId}/cahiers") //les cahhiers de tests liee au cahier de test global
    public ResponseEntity<List<CahierDeTest>> getCahiersByCahierDeTestGlobalId(@PathVariable Long cahierDeTestGlobalId) {
        List<CahierDeTest> cahiers = cahierDeTestService.findAllCahiersByCahierDeTestGlobalId(cahierDeTestGlobalId);
        if (cahiers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cahiers);
    }

    @GetMapping("/cahiers/{projectId}/{domaineId}")
    public ResponseEntity<?> findCahierByProjectAndDomain(@PathVariable Long projectId, @PathVariable Long domaineId) {
        try {
            List<CahierDeTest> cahiers = cahierDeTestService.findCahierByProjectAndDomain(projectId, domaineId);
            if (!cahiers.isEmpty()) {
                return ResponseEntity.ok(cahiers);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving CahierDeTests: " + e.getMessage());
        }
    }

//    @PostMapping("/cahierDeTest/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            cahierDeTestService.processExcelFile(file);
//            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and processed successfully.");
//        } catch (Exception e) {
//            logger.error("Error processing file: ", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and process file.");
//        }
//    }

    @PostMapping("/upload/{projectId}/{domaineId}")
    public ResponseEntity<Void> uploadExcelFile(
            @PathVariable Long projectId,
            @PathVariable Long domaineId,
            @RequestParam("file") MultipartFile file) {
        try {
            cahierDeTestService.processExcelFile(file, projectId, domaineId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Fonctionnalité>> getAllFonctionnalites() {
        List<Fonctionnalité> fonctionnalites = cahierDeTestService.getAllFonctionnalites();
        return ResponseEntity.ok(fonctionnalites);
    }

}
