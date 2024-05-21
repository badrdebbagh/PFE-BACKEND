package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine , Long> {
    Optional<Domaine> findByNom(String nom);

    Optional<Domaine> findById(Long id);

//    Set<Domaine> findByUsers_Id(Long userId);

    @Query("SELECT d FROM Domaine d JOIN d.users u WHERE u.id = :userId")
    Set<Domaine> findByUsers_Id(@Param("userId") Long userId);

    @Query(value = "SELECT d.* FROM domaines d JOIN user_domaine ud ON d.id = ud.domaine_id WHERE ud.user_id = :userId AND d.projet_id IN :projectIds", nativeQuery = true)
    List<Domaine> findDomainsByUserIdAndProjectIds(@Param("userId") Long userId, @Param("projectIds") List<Long> projectIds);


}
