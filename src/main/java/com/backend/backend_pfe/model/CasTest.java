package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cas_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String etapes;

    private String resultatAttendu;

    @ManyToOne
    @JoinColumn(name = "domaine_id", nullable = false)
    private Domaine domaine;

}
