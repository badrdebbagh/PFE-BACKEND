package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class CasTestServiceImpl implements CasTestService{


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private CasTestRepository casTestRepository;

    @Autowired
    private DomaineRepository domaineRepository;

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private ProjectRepository projetRepository;

    @Autowired
    private CahierDeTestRepository cahierDeTestRepository;

    private static final Logger logger = LoggerFactory.getLogger(CasTestServiceImpl.class);

    public CasTestServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CasTest createCasTest(String titre, String description, Long domaineId, Long fonctionnaliteId, Long projectId, Long cahierDeTestId) {
        logger.info("Creating CasTest with titre: {}, description: {}, domaineId: {}, fonctionnaliteId: {}, projectId: {}, cahierDeTestId: {}",
                titre, description, domaineId, fonctionnaliteId, projectId, cahierDeTestId);

        Domaine domaine = domaineRepository.findById(domaineId).orElseThrow(() -> new RuntimeException("Domaine not found"));
        Fonctionnalité fonctionnalite = fonctionnaliteRepository.findById(fonctionnaliteId).orElseThrow(() -> new RuntimeException("Fonctionnalité not found"));
        Projet projet = projetRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Projet not found"));
        CahierDeTest cahierDeTest = cahierDeTestRepository.findById(cahierDeTestId).orElseThrow(() -> new RuntimeException("CahierDeTest not found"));
        CasTest casTest = new CasTest();
        casTest.setTitre(titre);
        casTest.setDescription(description);
        casTest.setDomaine(domaine);
        casTest.setFonctionnalite(fonctionnalite);
        casTest.setProjet(projet);
        casTest.setCahierDeTest(cahierDeTest);

        return casTestRepository.save(casTest);
    }


    @Override
    public List<CasTest> getCasTests(Long domaineId, Long projectId, Long cahierDeTestId, Long fonctionnaliteId) {
        return casTestRepository.findByDomaineIdAndProjetIdAndCahierDeTestIdAndFonctionnaliteId(domaineId, projectId, cahierDeTestId, fonctionnaliteId);
    }

    @Override
    public List<CasTest> getCasTestsForUser(Long userId) {
        // Fetch the user
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the domaines for the user
        Set<Domaine> domaines = domaineRepository.findByUsers_Id(userId);

        // Collect all casTests
        List<CasTest> casTests = new ArrayList<>();

        // Fetch Cahiers de Tests in each Domaine
        for (Domaine domaine : domaines) {
            Set<CahierDeTest> cahiersDeTests = cahierDeTestRepository.findByDomaine_Id(domaine.getId());

            // Fetch Fonctionnalités in each Cahier de Test
            for (CahierDeTest cahierDeTest : cahiersDeTests) {
                Set<Fonctionnalité> fonctionnalites = fonctionnaliteRepository.findByCahierDeTest_Id(cahierDeTest.getId());

                // Fetch CasTests in each Fonctionnalité
                for (Fonctionnalité fonctionnalite : fonctionnalites) {
                    casTests.addAll(casTestRepository.findByFonctionnalite_Id(fonctionnalite.getId()));
                }
            }
        }

        return casTests;
    }

    @Override
    public List<CasTest> getTestCasesForUser(String username) {
        UserModel user = userRepository.findByEmail(username);
        return casTestRepository.findByUserModel(user);
    }

    @Override
    public List<CasTest> getAllTestCases() {
        return casTestRepository.findAll();
    }

    @Override
    public void executeTestCase(Long testCaseId) {
        CasTest testCase = casTestRepository.findById(testCaseId)
                .orElseThrow(() -> new RuntimeException("TestCase not found with id " + testCaseId));
        testCase.setStatus("Executed");
        casTestRepository.save(testCase);
    }

    @Override
    public void assignTestCaseToUser(Long testCaseId, String username) {
        CasTest testCase = casTestRepository.findById(testCaseId)
                .orElseThrow(() -> new RuntimeException("TestCase not found with id " + testCaseId));
        UserModel user = userRepository.findByEmail(username);
        testCase.setUserModel(user);
        casTestRepository.save(testCase);
    }
}
