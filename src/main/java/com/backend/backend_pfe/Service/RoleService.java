package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.USER_ROLE;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public interface RoleService {


    List<String> getAllRoles();
}