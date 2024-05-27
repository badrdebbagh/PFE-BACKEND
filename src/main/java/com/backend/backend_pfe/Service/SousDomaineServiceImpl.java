package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.model.SousDomaine;
import com.backend.backend_pfe.repository.SousDomaineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SousDomaineServiceImpl implements SousDomaineService {

    private final SousDomaineRepository sousDomaineRepository;

    public SousDomaineServiceImpl(SousDomaineRepository sousDomaineRepository) {
        this.sousDomaineRepository = sousDomaineRepository;
    }

    @Override
    public List<SousDomaine> getAllDomaines() {
        return sousDomaineRepository.findAll();
    }

    @Override
    public SousDomaine createSousDomaine(SousDomaine sousDomaine) {
        return sousDomaineRepository.save(sousDomaine);
    }
}
