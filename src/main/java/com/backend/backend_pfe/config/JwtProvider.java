package com.backend.backend_pfe.config;

import com.backend.backend_pfe.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication auth){
        UserModel user = (UserModel) auth.getPrincipal();
        Collection<? extends GrantedAuthority>authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 8640000))
                .claim("userId", user.getId())
                .claim("email" , auth.getName())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("authorities" , roles)
                .signWith(key)
                .compact();
        System.out.println("JWT Generated for user: " + auth.getName() + " | Token: " + jwt);
return jwt;
    }
    public String getEmailFromJwtToken(String jwt){
        jwt = jwt.substring(7);

        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private String populateAuthorities(Collection< ? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}
