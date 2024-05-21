package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.*;
import com.backend.backend_pfe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAssignmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CasTestRepository casTestRepository;

    @Autowired
    private FonctionnaliteRepository fonctionnaliteRepository;



//
}