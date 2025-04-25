package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.UserRegistrationDTO;

public interface AuthenticationService {

    void register(UserRegistrationDTO userRegistrationDTO);
}
