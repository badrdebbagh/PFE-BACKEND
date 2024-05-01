package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.repository.CahierDeTestGlobalRepository;
import com.backend.backend_pfe.repository.CahierDeTestRepository;
import com.backend.backend_pfe.repository.DomaineRepository;
import com.backend.backend_pfe.repository.SousDomaineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CahierDeTestImpl implements CahierDeTestService{
    @Autowired
    private final CahierDeTestGlobalRepository cahierDeTestGlobalRepository;

    @Autowired
    private final CahierDeTestRepository cahierDeTestRepository;

    @Autowired
    private final DomaineRepository domaineRepository;

    @Autowired
    private final SousDomaineRepository sousDomaineRepository;

    public CahierDeTestImpl(CahierDeTestGlobalRepository cahierDeTestGlobalRepository, CahierDeTestRepository cahierDeTestRepository, DomaineRepository domaineRepository, SousDomaineRepository sousDomaineRepository) {
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.cahierDeTestRepository = cahierDeTestRepository;
        this.domaineRepository = domaineRepository;
        this.sousDomaineRepository = sousDomaineRepository;
    }

    @Override
    public CahierDeTest createSubCahierDeTest(Long cahierDeTestGlobalId, CahierDeTest cahierDeTest) {
        return cahierDeTestGlobalRepository.findById(cahierDeTestGlobalId).map(cahierDeTestGlobal -> {
            cahierDeTest.setCahierDeTestGlobal(cahierDeTestGlobal);
            return cahierDeTestRepository.save(cahierDeTest);
        }).orElseThrow(() -> new RuntimeException("TestBook not found"));
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
}
