package com.backend.backend_pfe.service2;
import com.backend.backend_pfe.model.Projet;
import com.backend.backend_pfe.model.USER_ROLE;
import com.backend.backend_pfe.model.UserModel;
import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServices implements UserDetailsService {
@Autowired
    private UserRepository userRepository;
    @Override
    public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Username received: " + username);
        UserModel user = userRepository.findByEmail(username);
        if (user ==null){
            throw new UsernameNotFoundException("user not found");
        }

        if (user.isSuspended()) {
            throw new UsernameNotFoundException("Account is suspended");
        }

        USER_ROLE role = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));


        return new User(user.getEmail() , user.getPassword() , authorities);
    }

    public Set<Projet> getCurrentUserProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming the username is the email used in UserModel
        return getProjectsForUser(username);
    }

    private Set<Projet> getProjectsForUser(String email) {
        UserModel user = userRepository.findByEmailWithProjects(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getProjets();
    }
}
