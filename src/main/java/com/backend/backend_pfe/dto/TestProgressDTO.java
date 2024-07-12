package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestProgressDTO {
    private long totalTests;
    private long passedTests;
    private long failedTests;
    private long notTestedTests;

}
