package com.backend.backend_pfe.config;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {
@Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception{
    http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize ->Authorize
                    .requestMatchers("/api/createUser").permitAll()
                    .requestMatchers("/api/deleteUser/{id}").permitAll()
                    .requestMatchers("/auth/signin").permitAll()
                    .requestMatchers("/api/allProjects").permitAll()
                    .requestMatchers("/api/createProject").permitAll()
                    .requestMatchers("/api/addUsersToProject/{projectId}").permitAll()
                    .requestMatchers("/api/createProjectUser").permitAll()
                    .requestMatchers("/api/user/project").permitAll()
                    .requestMatchers("/api/suspendUser/{id}").permitAll()
                    .requestMatchers("/api/allUsers/{userId}").permitAll()
                    .requestMatchers("/api/userProjects").permitAll()
                    .requestMatchers("/api/user/{userId}/project/{projectId}").permitAll()
                    .requestMatchers("/api/find/{email}").permitAll()
                    .requestMatchers("/api/allUsers").permitAll()
                    .requestMatchers("/api/roles").permitAll()
                    .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/api/**")
                    .authenticated()
                    .anyRequest().permitAll()
            ).addFilterBefore(new JwtTokenValidator() , BasicAuthenticationFilter.class )
            .csrf(csrf -> csrf.disable())
            .cors(cors ->cors.configurationSource(corsConfigurationSource()));
return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
return new CorsConfigurationSource() {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(Arrays.asList(
                "http://localhost:5001"
        ));
        cfg.setAllowedMethods(Collections.singletonList("*"));
        cfg.setAllowCredentials(true);
        cfg.setAllowedHeaders(Collections.singletonList("*"));
        cfg.setExposedHeaders(Arrays.asList("Authorization"));
        cfg.setMaxAge(3600L);
        return cfg;
    }
};

    }
@Bean
    PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
    }
}