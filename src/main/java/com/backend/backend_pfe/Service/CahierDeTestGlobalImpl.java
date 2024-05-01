package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.CahierDeTestGlobal;
import com.backend.backend_pfe.repository.CahierDeTestGlobalRepository;
import com.backend.backend_pfe.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CahierDeTestGlobalImpl implements CahierDeTestGlobalService{

    @Autowired
    private final CahierDeTestGlobalRepository cahierDeTestGlobalRepository;
    @Autowired
    private final ProjectRepository projectRepository;

    public CahierDeTestGlobalImpl(CahierDeTestGlobalRepository cahierDeTestGlobalRepository, ProjectRepository projectRepository) {
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public CahierDeTestGlobal createGlobalCahierDeTest(Long projectId) {
        return projectRepository.findById(projectId).map(project -> {
            CahierDeTestGlobal cahierDeTestGlobal = new CahierDeTestGlobal();
            cahierDeTestGlobal.setProject(project);
            return cahierDeTestGlobalRepository.save(cahierDeTestGlobal);
        }).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public CahierDeTestGlobal findCahierDeTestGlobal(Long id) {
        return cahierDeTestGlobalRepository.findById(id).orElse(null);

    }


}
