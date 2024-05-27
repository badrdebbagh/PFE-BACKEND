package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "resultats_tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultatTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statut;
    private String commentaires;
    private LocalDate dateExecution;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "cas_test_id", nullable = false)
    private CasTest casTest;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UserModel utilisateur;


}



