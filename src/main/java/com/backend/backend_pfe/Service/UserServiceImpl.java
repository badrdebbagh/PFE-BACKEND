package com.backend.backend_pfe.Service;


import com.backend.backend_pfe.dto.UserProjectDTO;
import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProjectAssignmentRepository projectAssignmentRepository;

    @Autowired
    private final DomaineRepository domaineRepository;


    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final CasTestRepository casTestRepository;

    @Autowired
    private final CahierDeTestRepository cahierDeTestRepository;

    @Autowired
    private final FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private final TestCaseDescriptionRepository testCaseDescriptionRepository;

    public UserServiceImpl(UserRepository userRepository , PasswordEncoder passwordEncoder, ProjectAssignmentRepository projectAssignmentRepository, DomaineRepository domaineRepository, ProjectRepository projectRepository, CasTestRepository casTestRepository, CahierDeTestRepository cahierDeTestRepository, FonctionnaliteRepository fonctionnaliteRepository, TestCaseDescriptionRepository testCaseDescriptionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectAssignmentRepository = projectAssignmentRepository;
        this.domaineRepository = domaineRepository;

        this.projectRepository = projectRepository;
        this.casTestRepository = casTestRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
        this.fonctionnaliteRepository = fonctionnaliteRepository;
        this.testCaseDescriptionRepository = testCaseDescriptionRepository;
    }

    @Override
    public ResponseEntity<List<UserModel>>getAllUsers() {
        try {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK) ;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>() , HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel) {
        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        UserModel newUser = userRepository.save(userModel);

return new ResponseEntity<>(newUser  , HttpStatus.CREATED);
    }

    @Override
    public UserModel findUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }



    @Transactional
    @Override
    public UserModel assignProjectToEmployee(Long userId, Long projectId, USER_ROLE_PROJECTS role) {

        UserModel userModel = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Projet project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setUser(userModel);
        assignment.setProject(project);
        assignment.setRole(role);

        projectAssignmentRepository.save(assignment);
        System.out.println("Assigning role: " + role + " to user " + userModel.getEmail() + " for project " + project.getNom());


        return userModel;
    }


    public ResponseEntity<Void> suspendUser(Long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setSuspended(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> activateUser(Long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setSuspended(false);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public UserModel assignDomaineToUser(Long userId, Long domaineId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Domaine domaine = domaineRepository.findById(domaineId).orElseThrow(() -> new RuntimeException("Domaine not found"));

        user.getDomaines().add(domaine);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public UserModel assignUserToDomaineAndProject(Long userId, Long domaineId, Long projectId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Domaine domaine = domaineRepository.findById(domaineId).orElseThrow(() -> new RuntimeException("Domaine not found"));
        Projet projet = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Projet not found"));

        if (!projet.getDomaines().contains(domaine)) {
            throw new RuntimeException("Domaine not assigned to project");
        }

        user.getDomaines().add(domaine);
        domaine.getUsers().add(user);

        boolean isProjectAssigned = user.getProjectAssignments().stream()
                .anyMatch(assignment -> assignment.getProject().getId().equals(projectId));

        if (!isProjectAssigned) {
            ProjectAssignment projectAssignment = new ProjectAssignment();
            projectAssignment.setUser(user);
            projectAssignment.setProject(projet);
            user.getProjectAssignments().add(projectAssignment);
            projet.getProjectAssignments().add(projectAssignment);
            projectAssignmentRepository.save(projectAssignment);
        }

        // Assign all related test cases to the user
        List<CasTest> testCases = casTestRepository.findByProjetId(projectId);
        for (CasTest testCase : testCases) {
            testCase.setUserModel(user);
            casTestRepository.save(testCase);
        }

        userRepository.save(user);
        domaineRepository.save(domaine);
        projectRepository.save(projet);

        return user;
    }

    @Override
    public List<CasTest> getTestCasesForUser(Long userId) {
        return casTestRepository.findByUserModelId(userId);
    }

    @Override
    public List<CasTest> getTestCasesForUserAndProject(Long userId, Long projectId) {
        return casTestRepository.findByUserModelIdAndProjetId(userId, projectId);
    }

//    @Override
//    public UserProjectDTO getUserProjectsData(Long userId) {
//        List<Projet> projets = userRepository.findProjectsByUserId(userId);
//        List<Long> projectIds = projets.stream().map(Projet::getId).collect(Collectors.toList());
//        Set<Long> domainIdsSet = projets.stream().flatMap(projet -> projet.getDomaines().stream()).map(Domaine::getId).collect(Collectors.toSet());
//        List<Long> domainIds = new ArrayList<>(domainIdsSet);
//
//        // Fetch cahier de tests related to these domains and projects
//        List<CahierDeTest> cahierDeTests = cahierDeTestRepository.findCahierDeTestsByDomainIdsAndProjectIds(domainIds, projectIds);
//        List<Long> cahierDeTestIds = cahierDeTests.stream().map(CahierDeTest::getId).collect(Collectors.toList());
//
//        // Fetch functionalities related to each cahier de test
//        List<Fonctionnalité> fonctionnalites = fonctionnaliteRepository.findFonctionnalitesByCahierDeTestIds(cahierDeTestIds);
//        List<Long> fonctionnaliteIds = fonctionnalites.stream().map(Fonctionnalité::getId).collect(Collectors.toList());
//
//        // Fetch test cases related to each functionality
//        List<CasTest> casTests = casTestRepository.findCasTestsByFonctionnaliteIds(fonctionnaliteIds);
//
//        // Populate the functionalities with their corresponding test cases
//        Map<Long, List<CasTest>> casTestsByFonctionnaliteId = casTests.stream()
//                .collect(Collectors.groupingBy(casTest -> casTest.getFonctionnalite().getId()));
//
//        fonctionnalites.forEach(f -> f.setCasTests(casTestsByFonctionnaliteId.get(f.getId())));
//
//        // Populate the CahierDeTest with functionalities
//        Map<Long, List<Fonctionnalité>> fonctionnalitesByCahierDeTestId = fonctionnalites.stream()
//                .collect(Collectors.groupingBy(f -> f.getCahierDeTest().getId()));
//
//        cahierDeTests.forEach(cdt -> cdt.setFonctionnalites(fonctionnalitesByCahierDeTestId.get(cdt.getId())));
//
//        // Structure the data
//        UserProjectDTO userProjectDTO = new UserProjectDTO(projets, cahierDeTests, fonctionnalites, casTests);
//        return userProjectDTO;
//    }

//    @Override
//    public UserProjectDTO getUserProjectsData(Long userId) {
//        // Fetch projects assigned to the user
//        List<Projet> projets = userRepository.findProjectsByUserId(userId);
//        List<Long> projectIds = projets.stream().map(Projet::getId).collect(Collectors.toList());
//
//        // Fetch domains assigned to the user
//        Set<Domaine> userDomaines = domaineRepository.findByUsers_Id(userId);
//        List<Long> domainIds = userDomaines.stream().map(Domaine::getId).collect(Collectors.toList());
//
//        // Fetch cahier de tests related to these domains and projects
//        List<CahierDeTest> cahierDeTests = cahierDeTestRepository.findCahierDeTestsByDomainIdsAndProjectIds(domainIds, projectIds);
//        List<Long> cahierDeTestIds = cahierDeTests.stream().map(CahierDeTest::getId).collect(Collectors.toList());
//
//        // Map the cahier de tests back to projects
//        Map<Long, List<CahierDeTest>> cahierDeTestsByProjectId = cahierDeTests.stream()
//                .collect(Collectors.groupingBy(cdt -> cdt.getProjet().getId()));
//
//        // Fetch functionalities related to each cahier de test
//        List<Fonctionnalité> fonctionnalites = fonctionnaliteRepository.findFonctionnalitesByCahierDeTestIds(cahierDeTestIds);
//        List<Long> fonctionnaliteIds = fonctionnalites.stream().map(Fonctionnalité::getId).collect(Collectors.toList());
//
//        // Fetch test cases related to each functionality
//        List<CasTest> casTests = casTestRepository.findCasTestsByFonctionnaliteIds(fonctionnaliteIds);
//
//        // Populate the functionalities with their corresponding test cases
//        Map<Long, List<CasTest>> casTestsByFonctionnaliteId = casTests.stream()
//                .collect(Collectors.groupingBy(casTest -> casTest.getFonctionnalite().getId()));
//
//        fonctionnalites.forEach(f -> f.setCasTests(casTestsByFonctionnaliteId.get(f.getId())));
//
//        // Populate the CahierDeTest with functionalities
//        Map<Long, List<Fonctionnalité>> fonctionnalitesByCahierDeTestId = fonctionnalites.stream()
//                .collect(Collectors.groupingBy(f -> f.getCahierDeTest().getId()));
//
//        cahierDeTests.forEach(cdt -> cdt.setFonctionnalites(fonctionnalitesByCahierDeTestId.get(cdt.getId())));
//
//        // Assign the domains to their respective projects
//        projets.forEach(projet -> projet.setDomaines(
//                projet.getDomaines().stream()
//                        .filter(domain -> userDomaines.contains(domain))
//                        .collect(Collectors.toSet())
//        ));
//
//        // Structure the data
//        UserProjectDTO userProjectDTO = new UserProjectDTO(projets, cahierDeTests, fonctionnalites, casTests);
//        return userProjectDTO;
//    }


    @Override
    public UserProjectDTO getUserProjectsData(Long userId) {
        // Fetch projects assigned to the user
        List<Projet> projets = userRepository.findProjectsByUserId(userId);
        List<Long> projectIds = projets.stream().map(Projet::getId).collect(Collectors.toList());

        // Fetch domains assigned to the user
        Set<Domaine> userDomaines = domaineRepository.findByUsers_Id(userId);
        List<Long> domainIds = userDomaines.stream().map(Domaine::getId).collect(Collectors.toList());

        // Fetch cahier de tests related to these domains and projects
        List<CahierDeTest> cahierDeTests = cahierDeTestRepository.findCahierDeTestsByDomainIdsAndProjectIds(domainIds, projectIds);
        List<Long> cahierDeTestIds = cahierDeTests.stream().map(CahierDeTest::getId).collect(Collectors.toList());

        // Fetch functionalities related to each cahier de test
        List<Fonctionnalité> fonctionnalites = fonctionnaliteRepository.findFonctionnalitesByCahierDeTestIds(cahierDeTestIds);
        List<Long> fonctionnaliteIds = fonctionnalites.stream().map(Fonctionnalité::getId).collect(Collectors.toList());

        // Fetch test cases related to each functionality
        List<CasTest> casTests = casTestRepository.findCasTestsByFonctionnaliteIds(fonctionnaliteIds);
        List<Long> casTestIds = casTests.stream().map(CasTest::getId).collect(Collectors.toList());

        // Fetch test case descriptions related to each test case
        List<TestCaseDescription> testCaseDescriptions = testCaseDescriptionRepository.findByCasTest_IdIn(casTestIds);

        // Populate the test cases with their corresponding descriptions
        Map<Long, List<TestCaseDescription>> testCaseDescriptionsByCasTestId = testCaseDescriptions.stream()
                .collect(Collectors.groupingBy(testCaseDescription -> testCaseDescription.getCasTest().getId()));

        casTests.forEach(ct -> ct.setTestCaseDescriptions(testCaseDescriptionsByCasTestId.get(ct.getId())));

        // Populate the functionalities with their corresponding test cases
        Map<Long, List<CasTest>> casTestsByFonctionnaliteId = casTests.stream()
                .collect(Collectors.groupingBy(casTest -> casTest.getFonctionnalite().getId()));

        fonctionnalites.forEach(f -> f.setCasTests(casTestsByFonctionnaliteId.get(f.getId())));

        // Populate the CahierDeTest with functionalities
        Map<Long, List<Fonctionnalité>> fonctionnalitesByCahierDeTestId = fonctionnalites.stream()
                .collect(Collectors.groupingBy(f -> f.getCahierDeTest().getId()));

        cahierDeTests.forEach(cdt -> cdt.setFonctionnalites(fonctionnalitesByCahierDeTestId.get(cdt.getId())));

        // Assign the domains to their respective projects
        projets.forEach(projet -> projet.setDomaines(
                projet.getDomaines().stream()
                        .filter(domain -> userDomaines.contains(domain))
                        .collect(Collectors.toSet())
        ));

        // Structure the data
        UserProjectDTO userProjectDTO = new UserProjectDTO(projets, cahierDeTests, fonctionnalites, casTests);
        return userProjectDTO;
    }

    @Override
    public long countTotalUsers() {
        return userRepository.count();
    }


}


