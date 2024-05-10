package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine , Long> {
    Optional<Domaine> findByNom(String nom);

    Optional<Domaine> findById(Long id);
}
