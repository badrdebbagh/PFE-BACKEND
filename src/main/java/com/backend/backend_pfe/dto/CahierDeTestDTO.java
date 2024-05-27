package com.backend.backend_pfe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CahierDeTestDTO {

private Long cahierDeTestGlobalId;
        private String name;
        private String domaineName;
        private String sousDomaineName;
        private DomaineDTO domaineDTO;
        private Long domaineId;
        private Long sousDomaineId;
        private Long projectId;

        private DomaineDTO domaine;
        public CahierDeTestDTO(Long cahierDeTestGlobalId, String name, String domaineName, String sousDomaineName, DomaineDTO domaineDTO) {
                this.cahierDeTestGlobalId = cahierDeTestGlobalId;
                this.name = name;
                this.domaineName = domaineName;
                this.sousDomaineName = sousDomaineName;
                this.domaineDTO = domaineDTO;
        }





}
