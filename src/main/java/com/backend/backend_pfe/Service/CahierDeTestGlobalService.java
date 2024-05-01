package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
import com.backend.backend_pfe.repository.CahierDeTestGlobalRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface CahierDeTestGlobalService {

    CahierDeTestGlobal createGlobalCahierDeTest(Long projectId);

    CahierDeTestGlobal findCahierDeTestGlobal(Long id);
}
