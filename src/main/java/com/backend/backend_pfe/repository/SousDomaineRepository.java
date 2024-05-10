package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.SousDomaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SousDomaineRepository extends JpaRepository<SousDomaine , Long> {
    Optional<SousDomaine> findByName(String name);

    Optional<SousDomaine> findById(Long id);
}
