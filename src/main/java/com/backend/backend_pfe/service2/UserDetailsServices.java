package com.backend.backend_pfe.service2;
import com.backend.backend_pfe.dto.DomaineDTO;
import com.backend.backend_pfe.dto.ProjectDTO;
import com.backend.backend_pfe.dto.ProjectRoleDTO;
import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.CahierDeTestGlobalRepository;
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
import java.util.stream.Collectors;

@Service
public class UserDetailsServices implements UserDetailsService {
@Autowired
    private UserRepository userRepository;
@Autowired
    private CahierDeTestGlobalRepository cahierDeTestGlobalRepository;

    public UserDetailsServices(CahierDeTestGlobalRepository cahierDeTestGlobalRepositoryl) {
        this.cahierDeTestGlobalRepository = cahierDeTestGlobalRepository;
    }

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


        return user;
    }

    public Set<ProjectDTO> getCurrentUserProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming the username is the email used in UserModel
        return getProjectsForUser(email);


    }
// get projects of user with domaines assigned
    private Set<ProjectDTO> getProjectsForUser(String email) {
        UserModel user = userRepository.findByEmailWithProjectAssignments(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getProjectAssignments().stream()
                .map(assignment -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setProjectId(assignment.getProject().getId());
                    projectDTO.setProjectName(assignment.getProject().getNom());
                    projectDTO.setDescription(assignment.getProject().getDescription());


                    CahierDeTestGlobal cahier = cahierDeTestGlobalRepository.findByProjectId(assignment.getProject().getId());
                    if (cahier != null) {
                        projectDTO.setCahierDeTestGlobalId(cahier.getId());
                        projectDTO.setCahierDeTestGlobalNom(cahier.getNom());
                    }

                    // Set linked domains
                    Set<DomaineDTO> domaines = assignment.getProject().getDomaines().stream()
                            .filter(domaine -> domaine.getUsers().contains(user))
                            .map(domaine -> {
                                DomaineDTO domaineDTO = new DomaineDTO();
                                domaineDTO.setId(domaine.getId());
                                domaineDTO.setNom(domaine.getNom());
                                return domaineDTO;
                            })
                            .collect(Collectors.toSet());
                    projectDTO.setDomaines(domaines);

                    return projectDTO;
                })
                .collect(Collectors.toSet());
    }

    public Set<ProjectDTO> getUserProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Assuming the username is the email used in UserModel
        return getProjectsWithUser(email);
    }


    //get projects for logged user

    public Set<ProjectDTO> getProjectsWithUser(String email) {
        UserModel user = userRepository.findByEmailWithProjectAssignments(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getProjectAssignments().stream()
                .map(assignment -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setProjectId(assignment.getProject().getId());
                    projectDTO.setProjectName(assignment.getProject().getNom());
                    projectDTO.setDescription(assignment.getProject().getDescription());

                    // Fetch and set CahierDeTestGlobal ID
                    CahierDeTestGlobal cahier = cahierDeTestGlobalRepository.findByProjectId(assignment.getProject().getId());
                    if (cahier != null) {
                        projectDTO.setCahierDeTestGlobalId(cahier.getId());
                        projectDTO.setCahierDeTestGlobalNom(cahier.getNom());
                    }

                    // Set linked domains
                    Set<DomaineDTO> domaines = assignment.getProject().getDomaines().stream()
                            .map(domaine -> {
                                DomaineDTO domaineDTO = new DomaineDTO();
                                domaineDTO.setId(domaine.getId());
                                domaineDTO.setNom(domaine.getNom());
                                return domaineDTO;
                            })
                            .collect(Collectors.toSet());
                    projectDTO.setDomaines(domaines);

                    return projectDTO;
                })
                .collect(Collectors.toSet());
    }


}
