package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestPlanDTO {
    private String name;
    private String description;
    private Long projectId;
    // Getters and setters
}
