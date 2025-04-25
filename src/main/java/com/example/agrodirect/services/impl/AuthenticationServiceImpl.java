package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.UserRegistrationDTO;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.UserRepository;
import com.example.agrodirect.services.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuthenticationServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserRegistrationDTO userRegistrationDTO) {
        User user = modelMapper.map(userRegistrationDTO, User.class);
        userRepository.save(user);
    }
}
