package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.dto.CahierDeTestDTO;
import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.Fonctionnalité;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

//    void processExcelFile(MultipartFile file) throws IOException;

    List<Fonctionnalité> getAllFonctionnalites();

    void processExcelFile(MultipartFile file, Long projectId, Long domaineId) throws IOException;
}
