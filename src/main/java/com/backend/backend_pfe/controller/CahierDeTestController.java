package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.CahierDeTestService;
import com.backend.backend_pfe.model.CahierDeTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CahierDeTestController {

    @Autowired
    private CahierDeTestService cahierDeTestService;
    @GetMapping("cahiersDeTest")
    public ResponseEntity<List<CahierDeTest>> getAllCahiersDeTest() {
        List<CahierDeTest> cahiers = cahierDeTestService.getAllCahiersDeTest();
        if (cahiers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cahiers);
    }

    @PostMapping("/create/{cahierDeTestGlobalId}") // creer et lier un sous cahier de test a un cahier de test global
    public ResponseEntity<CahierDeTest> createSubCahierDeTest(@PathVariable Long cahierDeTestGlobalId, @RequestBody CahierDeTest cahierDeTest) {
        CahierDeTest newCahierDeTest = cahierDeTestService.createSubCahierDeTest(cahierDeTestGlobalId, cahierDeTest);
        return ResponseEntity.ok(newCahierDeTest);

    }
    @PostMapping("/setDomain/{cahierId}") // attribuer un domaine et un sous domaine au cahier de test selectionne
    public ResponseEntity<CahierDeTest> setDomainAndSubDomain(@PathVariable Long cahierId, @RequestParam Long domaineId, @RequestParam(required = false) Long subDomaineId) {
        CahierDeTest updatedCahier = cahierDeTestService.setDomainAndSubDomain(cahierId, domaineId, subDomaineId);
        return ResponseEntity.ok(updatedCahier);
    }

    @GetMapping("/cahierDeTestGlobal/{cahierDeTestGlobalId}/cahiers")
    public ResponseEntity<List<CahierDeTest>> getCahiersByCahierDeTestGlobalId(@PathVariable Long cahierDeTestGlobalId) {
        List<CahierDeTest> cahiers = cahierDeTestService.findAllCahiersByCahierDeTestGlobalId(cahierDeTestGlobalId);
        if (cahiers.isEmpty()) {
            return ResponseEntity.noContent().build();  // Return 204 No Content if no cahiers are found
        }
        return ResponseEntity.ok(cahiers);
    }


}
