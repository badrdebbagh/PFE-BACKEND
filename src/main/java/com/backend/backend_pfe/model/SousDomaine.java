package com.backend.backend_pfe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sous_domaines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SousDomaine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "domaine_id")
    private Domaine domaine;
}// Many SubDomaines belong to one Domaine


