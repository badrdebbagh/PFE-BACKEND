package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.Fonctionnalité;

import java.util.List;

public interface FonctionnaliteService {
    Fonctionnalité createFonctionnalité(String nom, Long domaineId , Long projectId ,Long cahierDeTestId);

    List<Fonctionnalité> getFunctionalitiesByProjectAndDomainAndCahierDeTest(Long domaineId , Long projectId ,Long cahierDeTestId);
}
