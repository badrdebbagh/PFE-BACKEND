package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.CahierDeTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CahierDeTestRepository extends JpaRepository<CahierDeTest , Long > {
    List<CahierDeTest> findByCahierDeTestGlobalId(Long cahierDeTestGlobalId);
}
