package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.ProjectStatus;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.UserModel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Projet , Long> {

//    List<Projet> findByNom(String nom);

    Optional<Projet> findByNom(String nom);


//    List<Projet> findByUtilisateurs_Id(Long userId);
@Query("select pa.project from ProjectAssignment pa where pa.user.id = :userId")
List<Projet> findProjectsByUserId(Long userId);

    @Query("SELECT p FROM Projet p LEFT JOIN FETCH p.domaines")
    List<Projet> findAllWithDomaines();

    @Query("SELECT COUNT(p) FROM Projet p WHERE p.status = :status")
    long countByStatus(@Param("status") ProjectStatus status);

    List<Projet> findByStatus(ProjectStatus status);

}
