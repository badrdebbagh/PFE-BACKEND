package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.Domaine;
import com.backend.backend_pfe.repository.DomaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DomaineServiceImpl implements DomaineService{
    private final DomaineRepository domaineRepository;



    public DomaineServiceImpl(DomaineRepository domaineRepository) {
        this.domaineRepository = domaineRepository;
    }


    @Override
    public List<Domaine> getAllDomaines() {
        return domaineRepository.findAll();
    }
}
