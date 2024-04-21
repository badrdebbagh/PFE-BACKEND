package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.UserModel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Projet , Long> {

    List<Projet> findByNom(String nom);


    List<Projet> findByUtilisateurs_Id(Long userId);
}
