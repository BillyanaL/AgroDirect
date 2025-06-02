package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.models.dtos.UserViewDTO;
import com.example.agrodirect.models.enums.UserRoles;

import java.util.List;

public interface UserService {

    boolean isUniqueEmail(String value);

    List<UserViewDTO> getUsersByRole(UserRoles role);

    void toggleUserStatus(Long userId);

    void deleteById(Long userId);

    void markUserAsDeleted(Long userId);


}
