package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.CahierDeTestGlobal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CahierDeTestGlobalRepository extends JpaRepository<CahierDeTestGlobal, Long> {

    CahierDeTestGlobal findByProjectId(Long projectId);
}
