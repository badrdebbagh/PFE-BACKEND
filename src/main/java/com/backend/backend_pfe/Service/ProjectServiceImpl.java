package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository ;

    private final CahierDeTestGlobalRepository cahierDeTestGlobalRepository;

    private final DomaineRepository domaineRepository;

    private final CahierDeTestRepository cahierDeTestRepository;

    private final CasTestRepository casTestRepository;
    private final TestResultRepository testResultRepository;
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, CahierDeTestGlobalRepository cahierDeTestGlobalRepository, DomaineRepository domaineRepository, CahierDeTestRepository cahierDeTestRepository, CasTestRepository casTestRepository, TestResultRepository testResultRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.domaineRepository = domaineRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
        this.casTestRepository = casTestRepository;
        this.testResultRepository = testResultRepository;
    }



    @Override
    public ResponseEntity<Projet> createProject(Projet projet) {
        Projet newProject = projectRepository.save(projet);
        return new ResponseEntity<>(newProject  , HttpStatus.CREATED);
    }

//    @Override
//    public List<Projet> getAllProjectsByUserId(Long userId) {
//        return projectRepository.findByUtilisateurs_Id(userId);
//    }

    @Override
    public List<Projet> getAllProjectsByUserId(Long userId) {
        return projectRepository.findProjectsByUserId(userId);
    }
    @Transactional
    public Projet createAndAssignProject(String projectName, String projectDescription, Long userId, USER_ROLE_PROJECTS role) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Projet newProject = new Projet(projectName);
        newProject.setDescription(projectDescription);

        // Assign the domaines to the project
//        List<Domaine> domaines = domaineRepository.findAllById(domaineIds);
//        newProject.setDomaines(new HashSet<>(domaines));

        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setUser(user);
        assignment.setProject(newProject);
        assignment.setRole(role);

        user.getProjectAssignments().add(assignment);
        newProject.getProjectAssignments().add(assignment);

        projectRepository.saveAndFlush(newProject);
        return newProject;
    }

    @Override
    public Optional<ProjectDTO> getProjectWithCahier(Long id) {
        return projectRepository.findById(id).map(projet -> {
            ProjectDTO dto = new ProjectDTO();
            dto.setProjectId(projet.getId());
            dto.setProjectName(projet.getNom());
            dto.setDescription(projet.getDescription());
            if (projet.getCahierDeTestGlobal() != null) {
                dto.setCahierDeTestGlobalId(projet.getCahierDeTestGlobal().getId());
                dto.setCahierDeTestGlobalNom(projet.getCahierDeTestGlobal().getNom());
            }
            return dto;
        });
    }

    @Override
    public CahierDeTestGlobal findCahierByProjectId(Long projectId) {
        return cahierDeTestGlobalRepository.findByProjectId(projectId);
    }

    @Override
    public Projet assignDomaineToProject(Long projectId, Long domaineId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Domaine domaine = domaineRepository.findById(domaineId)
                .orElseThrow(() -> new RuntimeException("Domain not found"));

        projet.getDomaines().add(domaine);
        return projectRepository.save(projet);
    }



    @Override
    public long countProjects() {
        return projectRepository.count();
    }

    @Override
    public ProjectStatus updateProjectStatus(Long projectId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Get all the CahierDeTests for the project
        List<CahierDeTest> cahierDeTests = cahierDeTestRepository.findByProjetId(projectId);

        // If there are no CahierDeTests, set status to EN_PROGRES and return
        if (cahierDeTests.isEmpty()) {
            projet.setStatus(ProjectStatus.EN_PROGRES);
            projectRepository.save(projet);
            return projet.getStatus();
        }

        // Collect all CasTests associated with the project's CahierDeTests
        List<CasTest> casTests = cahierDeTests.stream()
                .flatMap(cahierDeTest -> cahierDeTest.getFonctionnalites().stream())
                .flatMap(fonctionnalite -> fonctionnalite.getCasTests().stream())
                .collect(Collectors.toList());

        // If there are no CasTests, set status to EN_PROGRES and return
        if (casTests.isEmpty()) {
            projet.setStatus(ProjectStatus.EN_PROGRES);
            projectRepository.save(projet);
            return projet.getStatus();
        }

        // Collect all TestCaseDescriptions associated with the CasTests
        List<TestCaseDescription> testCaseDescriptions = casTests.stream()
                .flatMap(casTest -> casTest.getTestCaseDescriptions().stream())
                .collect(Collectors.toList());

        // If there are no TestCaseDescriptions, set status to EN_PROGRES and return
        if (testCaseDescriptions.isEmpty()) {
            projet.setStatus(ProjectStatus.EN_PROGRES);
            projectRepository.save(projet);
            return projet.getStatus();
        }

        // Get all TestCaseDescription IDs
        List<Long> testCaseDescriptionIds = testCaseDescriptions.stream()
                .map(TestCaseDescription::getId)
                .collect(Collectors.toList());

        // Fetch all TestResults associated with the TestCaseDescription IDs
        List<TestResult> resultatsTests = testResultRepository.findByTestCaseDescriptionIdIn(testCaseDescriptionIds);

        // Check if all test cases have been tested
        boolean allTested = casTests.stream().allMatch(casTest ->
                casTest.getTestCaseDescriptions().stream()
                        .allMatch(description -> resultatsTests.stream()
                                .anyMatch(result -> result.getTestCaseDescription().getId().equals(description.getId()) &&
                                        result.getStatus() != null)));

        if (!allTested) {
            projet.setStatus(ProjectStatus.EN_PROGRES);
        } else {
            boolean allOk = resultatsTests.stream().allMatch(resultatTest ->
                    resultatTest.getStatus() != null && resultatTest.getStatus().equals("OK"));
            boolean anyKo = resultatsTests.stream().anyMatch(resultatTest ->
                    resultatTest.getStatus() != null && resultatTest.getStatus().equals("KO"));

            if (allOk) {
                projet.setStatus(ProjectStatus.ACHEVEE);
            } else if (anyKo) {
                projet.setStatus(ProjectStatus.EN_PROGRES);
            } else {
                projet.setStatus(ProjectStatus.EN_PROGRES); // Default to in progress if there are no test results
            }
        }

        projectRepository.save(projet);
        return projet.getStatus();
    }


    @Override
    public long countCompletedProjects() {
        return projectRepository.countByStatus(ProjectStatus.ACHEVEE);
    }

    @Override
    public long countInProgressProjects() {
        return projectRepository.countByStatus(ProjectStatus.EN_PROGRES);
    }

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Override
    public Map<String, Map<String, Map<String, Long>>> countTestCasesByDomainAndSubdomain(Long projectId) {
        Projet projet = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Map<String, Map<String, Map<String, Long>>> result = new HashMap<>();

        // Fetch domains associated with the project
        Set<Domaine> domains = projet.getDomaines();
        logger.info("Fetched domains for project {}: {}", projectId, domains);

        for (Domaine domain : domains) {
            Map<String, Map<String, Long>> subdomainResult = new HashMap<>();

            // Fetch subdomains (CahierDeTests) associated with the domain and project
            List<CahierDeTest> cahierDeTests = cahierDeTestRepository.findByProjectIdAndDomaineId(projectId, domain.getId());
            logger.info("Fetched CahierDeTests for domain {}: {}", domain.getNom(), cahierDeTests);

            for (CahierDeTest cahierDeTest : cahierDeTests) {
                List<CasTest> casTests = casTestRepository.findByCahierDeTestId(cahierDeTest.getId());
                logger.info("Fetched CasTests for CahierDeTest {}: {}", cahierDeTest.getName(), casTests);

                long testedCount = 0;
                long notTestedCount = 0;

                for (CasTest casTest : casTests) {
                    List<TestCaseDescription> descriptions = casTest.getTestCaseDescriptions();
                    List<Long> descriptionIds = descriptions.stream().map(TestCaseDescription::getId).collect(Collectors.toList());
                    List<TestResult> testResults = testResultRepository.findByTestCaseDescriptionIdIn(descriptionIds);

                    if (testResults.isEmpty()) {
                        notTestedCount++;
                    } else {
                        testedCount++;
                    }
                }

                Map<String, Long> counts = new HashMap<>();
                counts.put("tested", testedCount);
                counts.put("notTested", notTestedCount);

                subdomainResult.put(cahierDeTest.getName(), counts);
            }
            result.put(domain.getNom(), subdomainResult);
        }

        logger.info("Final result for project {}: {}", projectId, result);
        return result;
    }

    @Override
    public List<Projet> getInProgressProjects() {
        return projectRepository.findByStatus(ProjectStatus.EN_PROGRES);
    }

    @Override
    public List<Projet> getCompletedProjects() {
        return projectRepository.findByStatus(ProjectStatus.ACHEVEE);
    }


    @Override
    public ResponseEntity<List<Projet>> getAllProjects() {
        try {
            List<Projet> projects = projectRepository.findAllWithDomaines();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }




}
