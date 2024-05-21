package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository

public interface CasTestRepository extends JpaRepository<CasTest, Long> {
    List<CasTest> findByDomaineIdAndProjetIdAndCahierDeTestIdAndFonctionnaliteId(Long domaineId, Long projetId, Long cahierDeTestId, Long fonctionnaliteId);

    List<CasTest> findByFonctionnalite_Id(Long fonctionnaliteId);


    List<CasTest> findByProjetId(Long projetId);
    List<CasTest> findByUserModel(UserModel userModel);

    List<CasTest> findByUserModelId(Long userId);

    List<CasTest> findByUserModelIdAndProjetId(Long userId, Long projetId);

    @Query("SELECT ct FROM CasTest ct WHERE ct.fonctionnalite.id IN :fonctionnaliteIds")
    List<CasTest> findCasTestsByFonctionnaliteIds(@Param("fonctionnaliteIds") List<Long> fonctionnaliteIds);
}

