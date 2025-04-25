package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.entities.Role;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBInitService {

    private final RoleRepository roleRepository;

    public DBInitService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(UserRoles.values()).forEach(enumRole -> {
                Role role = new Role();
                role.setName(enumRole);
                roleRepository.save(role);
            });
        }
    }
}
