package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TestIterationDTO {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    // Getters and setters
}
