package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.Fonctionnalité;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.repository.CahierDeTestRepository;
import com.backend.backend_pfe.repository.DomaineRepository;
import com.backend.backend_pfe.repository.FonctionnaliteRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class FonctionnaliteServiceImpl implements FonctionnaliteService {

    @Autowired
    private final FonctionnaliteRepository fonctionnaliteRepository;

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final DomaineRepository domaineRepository;

    @Autowired
    private final CahierDeTestRepository cahierDeTestRepository;

    public FonctionnaliteServiceImpl(FonctionnaliteRepository fonctionnaliteRepository, ProjectRepository projectRepository, DomaineRepository domaineRepository, CahierDeTestRepository cahierDeTestRepository) {
        this.fonctionnaliteRepository = fonctionnaliteRepository;
        this.projectRepository = projectRepository;
        this.domaineRepository = domaineRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
    }




    @Override
    public Fonctionnalité createFonctionnalité(String nom, Long domaineId, Long projectId, Long cahierDeTestId) {
        Projet project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Domaine domaine = domaineRepository.findById(domaineId)
                .orElseThrow(() -> new RuntimeException("Domaine not found"));

        CahierDeTest cahierDeTest = cahierDeTestRepository.findById(cahierDeTestId)
                .orElseThrow(() -> new RuntimeException("Cahier de test not found"));

        Fonctionnalité fonctionnalité = new Fonctionnalité();
        fonctionnalité.setNom(nom);
        fonctionnalité.setDomaine(domaine);
        fonctionnalité.setProjet(project);
        fonctionnalité.setCahierDeTest(cahierDeTest);
        fonctionnalité.setCasTests(new ArrayList<>());

        return fonctionnaliteRepository.save(fonctionnalité);
    }

    @Override
    public List<Fonctionnalité> getFunctionalitiesByProjectAndDomainAndCahierDeTest(Long domaineId , Long projectId , Long cahierDeTestId) {
        return fonctionnaliteRepository.findByDomaineIdAndProjetIdAndCahierDeTestId(domaineId, projectId , cahierDeTestId);
    }

}
