package com.backend.backend_pfe.dto;

import com.backend.backend_pfe.model.CahierDeTest;
import com.backend.backend_pfe.model.CasTest;
import com.backend.backend_pfe.model.Fonctionnalité;
import com.backend.backend_pfe.model.Projet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProjectDTO {

    private List<Projet> projets;
    private List<CahierDeTest> cahierDeTests;
    private List<Fonctionnalité> fonctionnalites;
    private List<CasTest> casTests;
}
