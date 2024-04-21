package com.backend.backend_pfe.controller;

import com.backend.backend_pfe.config.JwtProvider;
import com.backend.backend_pfe.model.USER_ROLE;
import com.backend.backend_pfe.repository.UserRepository;
import com.backend.backend_pfe.request.LoginRequest;
import com.backend.backend_pfe.response.AuthResponse;
import com.backend.backend_pfe.service2.UserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
@Autowired
    private UserRepository userRepository;

@Autowired
    private PasswordEncoder passwordEncoder;
@Autowired
    private JwtProvider jwtProvider;
@Autowired
    private UserDetailsServices userDetailsServices;

@PostMapping("/signin")
public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){

    String username = req.getEmail();
    String password = req.getPassword();

    Authentication authentication = authenticate(username , password);

    String jwt = jwtProvider.generateToken(authentication);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setJwt(jwt);
    authResponse.setMessage("register success");
    Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();

    String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
    authResponse.setRole(USER_ROLE.valueOf(role));


    return new ResponseEntity<>(authResponse, HttpStatus.OK);

}

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsServices.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }

        if(!passwordEncoder.matches(password , userDetails.getPassword())){
          throw new BadCredentialsException("invalid password ...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
    }
}
