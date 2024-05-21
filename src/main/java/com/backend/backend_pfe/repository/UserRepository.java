package com.backend.backend_pfe.repository;


import com.backend.backend_pfe.model.*;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    public UserModel findByEmail(String username);

//    @Query("SELECT u FROM UserModel u JOIN FETCH u.projets WHERE u.email = :email")
//    UserModel findByEmailWithProjects(@Param("email") String email);

    @Query("SELECT u FROM UserModel u LEFT JOIN FETCH u.projectAssignments pa LEFT JOIN FETCH pa.project WHERE u.email = :email")
    UserModel findByEmailWithProjectAssignments(@Param("email") String email);

    @Query("SELECT pa.project FROM ProjectAssignment pa WHERE pa.user.id = :userId")
    List<Projet> findProjectsByUserId(@Param("userId") Long userId);



}
