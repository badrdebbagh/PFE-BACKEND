package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.CahierDeTestDTO;
import com.backend.backend_pfe.dto.DomaineDTO;
import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.SousDomaine;
import com.backend.backend_pfe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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

    public CahierDeTestImpl(CahierDeTestGlobalRepository cahierDeTestGlobalRepository, CahierDeTestRepository cahierDeTestRepository, DomaineRepository domaineRepository, SousDomaineRepository sousDomaineRepository, ProjectRepository projectRepository) {
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
        this.domaineRepository = domaineRepository;
        this.sousDomaineRepository = sousDomaineRepository;
        this.projectRepository = projectRepository;
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
        cahier.setCahierDeTestGlobal(
                cahierDeTestGlobalRepository.findById(cahierDeTestDTO.getCahierDeTestGlobalId())
                        .orElseThrow(() -> new RuntimeException("TestBook Global not found"))
        );

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
