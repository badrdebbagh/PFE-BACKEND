package com.backend.backend_pfe.request;

import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import lombok.Data;

@Data
public class ProjetRequest {

        private String nom;
        private String description;
        private Long userId;

        private USER_ROLE_PROJECTS role;


}
