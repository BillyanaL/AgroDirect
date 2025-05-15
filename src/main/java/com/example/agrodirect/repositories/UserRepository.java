package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail (String email);

    List<User> findByRoles_Name (UserRoles roleName);

}
