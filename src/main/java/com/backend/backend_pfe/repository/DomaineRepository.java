package com.backend.backend_pfe.repository;

import com.backend.backend_pfe.model.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine , Long> {
}
