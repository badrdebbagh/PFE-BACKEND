package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImplementation implements  AdminService {

    @Autowired
    private final UserRepository userRepository;

    public AdminServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
