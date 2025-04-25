package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.entities.Role;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.RoleRepository;
import com.example.agrodirect.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl (RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName (String name) {

        return this.roleRepository.findByName(UserRoles.valueOf(name));
    }
}
