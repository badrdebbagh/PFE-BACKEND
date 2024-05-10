package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.CahierDeTestDTO;
import com.backend.backend_pfe.model.CahierDeTest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CahierDeTestService {
//    CahierDeTest createSubCahierDeTest(Long CahierDeTestGlobalId, CahierDeTest cahierDeTest);

    CahierDeTest setDomainAndSubDomain(Long cahierId, Long domaineId, Long subDomaineId);

    CahierDeTest findCahierDeTest(Long id);

    List<CahierDeTest> findAllCahiersByCahierDeTestGlobalId(Long cahierDeTestGlobalId);

    List<CahierDeTest> getAllCahiersDeTest();

//    CahierDeTest createSousCahierDeTest(CahierDeTestDTO cahierDeTestDTO);


    CahierDeTest createSubCahierDeTest(CahierDeTestDTO cahierDeTestDTO);


    List<CahierDeTest> findCahierByProjectAndDomain(Long projectId, Long domaineId);
}
