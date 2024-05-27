package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.SousDomaine;

import java.util.List;

public interface DomaineService {
    List<Domaine> getAllDomaines();

    Domaine createDomaine(Domaine domaine);
}
