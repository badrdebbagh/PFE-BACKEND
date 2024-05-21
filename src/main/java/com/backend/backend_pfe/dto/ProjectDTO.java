package com.backend.backend_pfe.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProjectDTO {
    private Long projectId;
    private String projectName;
    private String description;
    private String userRole;
    private Long cahierDeTestGlobalId;
    private String cahierDeTestGlobalNom;
    private Set<DomaineDTO> domaines;
}
