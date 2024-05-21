package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.CahierDeTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CahierDeTestRepository extends JpaRepository<CahierDeTest , Long > {
    List<CahierDeTest> findByCahierDeTestGlobalId(Long cahierDeTestGlobalId);


//    @Query("SELECT c FROM CahierDeTest c WHERE c.cahierDeTestGlobal.project.id = :projectId AND (:domaineId IS NULL OR c.domaine.id = :domaineId)")
//    List<CahierDeTest> findByProjectAndDomaine(@Param("projectId") Long projectId, @Param("domaineId") Long domaineId);



    @Query("SELECT c FROM CahierDeTest c WHERE c.projet.id = :projectId AND c.domaine.id = :domaineId")
    List<CahierDeTest> findByProjectIdAndDomaineId(@Param("projectId") Long projectId, @Param("domaineId") Long domaineId);

    Set<CahierDeTest> findByDomaine_Id(Long domaineId);

    @Query("SELECT cd FROM CahierDeTest cd WHERE cd.domaine.id IN :domainIds AND cd.projet.id IN :projectIds")
    List<CahierDeTest> findCahierDeTestsByDomainIdsAndProjectIds(@Param("domainIds") List<Long> domainIds, @Param("projectIds") List<Long> projectIds);
}
