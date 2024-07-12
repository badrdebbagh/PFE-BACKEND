package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTestCaseCountsDTO {
    private Long projectId;
    private String projectName;
    private long totalTestCases;
    private long passedTestCases;
    private long failedTestCases;
    private long notTestedTestCases;
}
