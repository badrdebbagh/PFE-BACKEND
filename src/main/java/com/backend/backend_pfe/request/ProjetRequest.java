package com.backend.backend_pfe.request;

import com.backend.backend_pfe.model.USER_ROLE_PROJECTS;
import lombok.Data;

import java.util.List;

@Data
public class ProjetRequest {

        private String projectName;
        private String description;
        private Long userId;
        private USER_ROLE_PROJECTS role;
        private List<Long> domaineIds;

}
