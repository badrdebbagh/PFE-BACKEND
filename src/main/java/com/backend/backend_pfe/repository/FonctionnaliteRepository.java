package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.Fonctionnalité;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface FonctionnaliteRepository extends JpaRepository<Fonctionnalité , Long> {

    @Query("SELECT f FROM Fonctionnalité f LEFT JOIN FETCH f.casTests WHERE f.domaine.id = :domaineId AND f.projet.id = :projectId AND f.cahierDeTest.id = :cahierDeTestId")
    List<Fonctionnalité> findByDomaineIdAndProjetIdAndCahierDeTestId(@Param("domaineId") Long domaineId, @Param("projectId") Long projectId, @Param("cahierDeTestId") Long cahierDeTestId);

    List<Fonctionnalité> findByProjetId(Long projetId);

    Set<Fonctionnalité> findByCahierDeTest_Id(Long cahierDeTestId);

    @Query("SELECT f FROM Fonctionnalité f WHERE f.cahierDeTest.id IN :cahierDeTestIds")
    List<Fonctionnalité> findFonctionnalitesByCahierDeTestIds(@Param("cahierDeTestIds") List<Long> cahierDeTestIds);
}
