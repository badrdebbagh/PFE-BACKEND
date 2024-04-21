package com.backend.backend_pfe.Service;

import com.backend.backend_pfe.model.USER_ROLE;
import org.apache.catalina.Role;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<String> getAllRoles() {
        return Arrays.stream(USER_ROLE.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
