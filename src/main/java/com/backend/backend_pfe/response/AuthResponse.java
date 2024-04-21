package com.backend.backend_pfe.response;

import com.backend.backend_pfe.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;

    private USER_ROLE role = USER_ROLE.USER ;
}
