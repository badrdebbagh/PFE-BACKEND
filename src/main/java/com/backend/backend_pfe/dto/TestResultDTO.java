package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDTO {

    private Long id;

    private String status; // OK or KO
    private Long testCaseDescriptionId;

    private String comment ;
    private Long cahierDeTestId;

}
