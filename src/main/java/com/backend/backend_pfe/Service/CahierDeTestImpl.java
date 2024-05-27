package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.CahierDeTestDTO;
import com.backend.backend_pfe.dto.DomaineDTO;
import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CahierDeTestImpl implements CahierDeTestService {
    @Autowired
    private final CahierDeTestGlobalRepository cahierDeTestGlobalRepository;

    @Autowired
    private final CahierDeTestRepository cahierDeTestRepository;

    @Autowired
    private final DomaineRepository domaineRepository;

    @Autowired
    private final SousDomaineRepository sousDomaineRepository;

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private final CasTestRepository casTestRepository;

    @Autowired
    private final TestCaseDescriptionRepository etapeRepository;






    private static final Logger logger = LoggerFactory.getLogger(CahierDeTestImpl.class);
    public CahierDeTestImpl(CahierDeTestGlobalRepository cahierDeTestGlobalRepository, CahierDeTestRepository cahierDeTestRepository, DomaineRepository domaineRepository, SousDomaineRepository sousDomaineRepository, ProjectRepository projectRepository, FonctionnaliteRepository fonctionnaliteRepository, CasTestRepository casTestRepository, TestCaseDescriptionRepository etapeRepository) {
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
        this.domaineRepository = domaineRepository;
        this.sousDomaineRepository = sousDomaineRepository;
        this.projectRepository = projectRepository;
        this.fonctionnaliteRepository = fonctionnaliteRepository;
        this.casTestRepository = casTestRepository;
        this.etapeRepository = etapeRepository;
    }

//    @Override
//    public CahierDeTest createSubCahierDeTest(Long cahierDeTestGlobalId, CahierDeTest cahierDeTest) {
//        return cahierDeTestGlobalRepository.findById(cahierDeTestGlobalId).map(cahierDeTestGlobal -> {
//            cahierDeTest.setCahierDeTestGlobal(cahierDeTestGlobal);
//            return cahierDeTestRepository.save(cahierDeTest);
//        }).orElseThrow(() -> new RuntimeException("TestBook not found"));
//    }


    @Override
    public CahierDeTest createSubCahierDeTest(CahierDeTestDTO cahierDeTestDTO) {
        CahierDeTest cahier = new CahierDeTest();
        cahier.setName(cahierDeTestDTO.getName());

        Optional<Projet> projetOptional = projectRepository.findById(cahierDeTestDTO.getProjectId());
        projetOptional.ifPresent(cahier::setProjet);

        Optional<Domaine> domaineOptional = domaineRepository.findById(cahierDeTestDTO.getDomaineId());
        domaineOptional.ifPresent(cahier::setDomaine);

            Optional<SousDomaine> sousDomaineOptional = sousDomaineRepository.findById(cahierDeTestDTO.getSousDomaineId());
            sousDomaineOptional.ifPresent(cahier::setSousDomaine);


        return cahierDeTestRepository.save(cahier);
    }

    @Override
    public List<CahierDeTest> findCahierByProjectAndDomain(Long projectId, Long domaineId) {
        return cahierDeTestRepository.findByProjectIdAndDomaineId(projectId, domaineId);
    }
    @Override
    public void processExcelFile(MultipartFile file, Long projectId, Long domaineId) throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String cahierDeTestName = row.getCell(0).getStringCellValue();
                String fonctionnaliteName = row.getCell(1).getStringCellValue();
                String casTestTitle = row.getCell(2).getStringCellValue();
                String stepDescription = row.getCell(3).getStringCellValue();
                String expectedResult = row.getCell(4).getStringCellValue();
                String sousDomaineName = row.getCell(5).getStringCellValue(); // Assuming this is the 6th column

                // Fetch or create Project
                Optional<Projet> projectOpt = projectRepository.findById(projectId);
                Projet project = projectOpt.orElseThrow(() -> new RuntimeException("Project not found"));

                // Fetch or create Domaine
                Optional<Domaine> domaineOpt = domaineRepository.findById(domaineId);
                Domaine domaine = domaineOpt.orElseThrow(() -> new RuntimeException("Domain not found"));

                // Fetch or create SousDomaine
                Optional<SousDomaine> sousDomaineOpt = sousDomaineRepository.findByName(sousDomaineName);
                SousDomaine sousDomaine;
                if (sousDomaineOpt.isPresent()) {
                    sousDomaine = sousDomaineOpt.get();
                } else {
                    sousDomaine = new SousDomaine();
                    sousDomaine.setName(sousDomaineName);
                    sousDomaine.setDomaine(domaine);
                    sousDomaine = sousDomaineRepository.save(sousDomaine);
                }

                // Fetch or create CahierDeTest
                Optional<CahierDeTest> cahierDeTestOpt = cahierDeTestRepository.findByNameAndDomaineAndProjet(cahierDeTestName, domaine, project);
                CahierDeTest cahierDeTest;
                if (cahierDeTestOpt.isPresent()) {
                    cahierDeTest = cahierDeTestOpt.get();
                } else {
                    cahierDeTest = new CahierDeTest();
                    cahierDeTest.setName(cahierDeTestName);
                    cahierDeTest.setDomaine(domaine);
                    cahierDeTest.setSousDomaine(sousDomaine);
                    cahierDeTest.setProjet(project);
                    cahierDeTest = cahierDeTestRepository.save(cahierDeTest);
                }

                // Fetch or create Fonctionnalite
                List<Fonctionnalité> fonctionnaliteList = fonctionnaliteRepository.findByName(fonctionnaliteName);
                Fonctionnalité fonctionnalite;
                if (!fonctionnaliteList.isEmpty()) {
                    fonctionnalite = fonctionnaliteList.get(0); // Take the first result
                } else {
                    fonctionnalite = new Fonctionnalité();
                    fonctionnalite.setNom(fonctionnaliteName);
                    fonctionnalite.setCahierDeTest(cahierDeTest);
                    fonctionnalite.setDomaine(domaine);
                    fonctionnalite.setProjet(project);
                    fonctionnalite = fonctionnaliteRepository.save(fonctionnalite);
                }

                // Create CasTest
                CasTest casTest = new CasTest();
                casTest.setTitre(casTestTitle);
                casTest.setFonctionnalite(fonctionnalite);
                casTest.setDomaine(domaine);
                casTest.setCahierDeTest(cahierDeTest);
                casTest.setProjet(project);
                casTest = casTestRepository.save(casTest);

                // Create TestCaseDescription (Step)
                TestCaseDescription etape = new TestCaseDescription();
                etape.setDescription(stepDescription);
                etape.setResultatAttendu(expectedResult);
                etape.setCasTest(casTest);
                etapeRepository.save(etape);
            }
        } catch (Exception e) {
            logger.error("Error processing Excel file: ", e);
            throw e;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }




    @Override
    public List<Fonctionnalité> getAllFonctionnalites() {
        return null;
    }


    @Override
    public CahierDeTest setDomainAndSubDomain(Long cahierId, Long domaineId, Long subDomaineId) {
        return cahierDeTestRepository.findById(cahierId).map(cahierDeTest -> {
            cahierDeTest.setDomaine(domaineRepository.findById(domaineId).orElseThrow(() -> new RuntimeException("Domain not found")));
            if (subDomaineId != null) {
                cahierDeTest.setSousDomaine(sousDomaineRepository.findById(subDomaineId).orElseThrow(() -> new RuntimeException("Sub-domain not found")));
            }
            return cahierDeTestRepository.save(cahierDeTest);
        }).orElseThrow(() -> new RuntimeException("Cahier de Tests not found"));
    }

    @Override
    public CahierDeTest findCahierDeTest(Long id) {
        return cahierDeTestRepository.findById(id).orElse(null);
    }

    public List<CahierDeTest> findAllCahiersByCahierDeTestGlobalId(Long cahierDeTestGlobalId) {
        return cahierDeTestRepository.findByCahierDeTestGlobalId(cahierDeTestGlobalId);
    }

    @Override
    public List<CahierDeTest> getAllCahiersDeTest() {
        return cahierDeTestRepository.findAll();
    }

//    @Override
//    public CahierDeTest createSousCahierDeTest(CahierDeTestDTO cahierDeTestDTO) {
//        CahierDeTest cahier = new CahierDeTest();
//        cahier.setName(cahierDeTestDTO.getName());
//        cahier.setCahierDeTestGlobal(cahierDeTestGlobalRepository.findById(cahierDeTestDTO.getCahierDeTestGlobalId()).orElseThrow());
//        cahier.setDomaine(domaineRepository.findByNom(cahierDeTestDTO.getDomaineName()).orElseThrow());
//        if (cahierDeTestDTO.getSousDomaineName() != null) {
//            cahier.setSousDomaine(sousDomaineRepository.findByName(cahierDeTestDTO.getSousDomaineName()).orElseThrow());
//        }
//        return cahierDeTestRepository.save(cahier);
//    }

//    public List<CahierDeTestDTO> findByProjectAndDomaine(Long projectId, Long domaineId) {
//        List<CahierDeTest> cahiers = cahierDeTestRepository.findByProjectAndDomaine(projectId, domaineId);
//        return cahiers.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }


    private CahierDeTestDTO toDto(CahierDeTest cahier) {
        Domaine domaine = cahier.getDomaine();
        DomaineDTO domaineDTO = domaine != null ? new DomaineDTO(domaine.getId(), domaine.getNom()) : null;
        String domaineName = domaine != null ? domaine.getNom() : null;
        String sousDomaineName = cahier.getSousDomaine() != null ? cahier.getSousDomaine().getName() : null;

        return new CahierDeTestDTO(
                cahier.getCahierDeTestGlobal() != null ? cahier.getCahierDeTestGlobal().getId() : null,
                cahier.getName(),
                domaineName,
                sousDomaineName,
                domaineDTO
        );
    }
}
