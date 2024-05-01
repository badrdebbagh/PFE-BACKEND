package com.backend.backend_pfe.dto;

import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleDTO {
    private Long projectId;
    private String projectName;
    private String description;
    private String userRole;

}