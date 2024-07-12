package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.Service.CasTestService;
import com.backend.backend_pfe.dto.TestProgressDTO;
import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CasTestController {

    @Autowired
    private CasTestService casTestService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createCasTest")
    public ResponseEntity<CasTest> createCasTest(@RequestParam String titre,
                                                 @RequestParam String description,
                                                 @RequestParam Long domaineId,
                                                 @RequestParam Long fonctionnaliteId,
                                                 @RequestParam Long projectId,
                                                 @RequestParam Long cahierDeTestId) {
        CasTest casTest = casTestService.createCasTest(titre, description, domaineId, fonctionnaliteId, projectId, cahierDeTestId);
        return ResponseEntity.ok(casTest);
    }

    @GetMapping("/casTests")
    public ResponseEntity<List<CasTest>> getCasTests(@RequestParam Long domaineId,
                                                     @RequestParam Long projectId,
                                                     @RequestParam Long cahierDeTestId,
                                                     @RequestParam Long fonctionnaliteId) {
        List<CasTest> casTests = casTestService.getCasTests(domaineId, projectId, cahierDeTestId, fonctionnaliteId);
        return ResponseEntity.ok(casTests);
    }

    @GetMapping("/user/{userId}/castests")
    public List<CasTest> getCasTestsForUser(@PathVariable Long userId) {
        return casTestService.getCasTestsForUser(userId);
    }

    @GetMapping("/user/testcases")
    public String getUserTestCases(Model model, Principal principal) {
        String username = principal.getName();
        UserModel user = userRepository.findByEmail(username);

        List<CasTest> testCases;
        if ("ADMIN".equals(user.getRole())) {
            testCases = casTestService.getAllTestCases();
        } else {
            testCases = casTestService.getTestCasesForUser(username);
        }

        model.addAttribute("testCases", testCases);
        return "userTestCases";
    }


    @PostMapping("/user/testcases/{id}/execute")
    public String executeTestCase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        casTestService.executeTestCase(id);
        redirectAttributes.addFlashAttribute("message", "Test case executed successfully!");
        return "redirect:/user/testcases";
    }

    @PostMapping("/admin/testcases/{id}/assign")
    public String assignTestCaseToUser(@PathVariable Long id, @RequestParam String username, RedirectAttributes redirectAttributes) {
        casTestService.assignTestCaseToUser(id, username);
        redirectAttributes.addFlashAttribute("message", "Test case assigned successfully!");
        return "redirect:/user/testcases";
    }



}
