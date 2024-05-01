package com.backend.backend_pfe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName ;
    private String lastName;
    private String email;
    private String password;

    @Enumerated
    private USER_ROLE role = USER_ROLE.ADMIN ;

    @Enumerated
    private USER_ROLE_PROJECTS roleProjects = USER_ROLE_PROJECTS.USER_ROLE_1;

    @JsonIgnore
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

private Set<ProjectAssignment> projectAssignments = new HashSet<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority( role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false; // You can customize this if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return false; // You can customize this if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false; // You can customize this if needed
    }

    @Override
    public boolean isEnabled() {
        return !isSuspended;  // You can customize this based on your user properties
    }

    @Column(name = "is_suspended")
    private boolean isSuspended = false;




//    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "projet_utilisateur", // Nom de la table de jointure
//            joinColumns = @JoinColumn(name = "utilisateur_id" ), // Clé étrangère pour UserModel
//            inverseJoinColumns = @JoinColumn(name = "projet_id" ) // Clé étrangère pour Projet
//    )
//    private Set<Projet> projets = new HashSet<>();


}
