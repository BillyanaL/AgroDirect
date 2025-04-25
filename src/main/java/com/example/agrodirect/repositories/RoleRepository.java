package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Role;
import com.example.agrodirect.models.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (UserRoles role);
}
