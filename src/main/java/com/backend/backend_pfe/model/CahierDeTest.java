package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Getter
@Setter
@Table(name = "cahiers_de_tests")
@NoArgsConstructor
@AllArgsConstructor
public class CahierDeTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cahier_de_test_global_id")
    private CahierDeTestGlobal cahierDeTestGlobal;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domaine_id")
    private Domaine domaine;

   @JsonIgnore
    @ManyToOne( fetch = FetchType.LAZY)
   @JoinColumn(name = "sous_domaine_id")
    private SousDomaine sousDomaine;
 @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Projet projet;



}
