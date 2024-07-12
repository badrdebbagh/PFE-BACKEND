package com.backend.backend_pfe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LastLoginDTO {
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
}
