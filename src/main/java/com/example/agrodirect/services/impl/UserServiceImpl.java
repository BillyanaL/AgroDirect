package com.example.agrodirect.services.impl;

import com.example.agrodirect.repositories.UserRepository;
import com.example.agrodirect.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean isUniqueEmail(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }
}
