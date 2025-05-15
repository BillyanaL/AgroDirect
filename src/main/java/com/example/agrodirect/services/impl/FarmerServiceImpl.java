package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.UserRepository;
import com.example.agrodirect.services.FarmerProfileService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FarmerServiceImpl implements FarmerProfileService {
    private final LoggedUserHelperService loggedUserHelperService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FarmerServiceImpl(LoggedUserHelperService loggedUserHelperService, UserRepository userRepository, ModelMapper modelMapper) {
        this.loggedUserHelperService = loggedUserHelperService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FarmerProfileDTO getCurrentFarmerProfile() {
        User currentUser = loggedUserHelperService.get();
        return modelMapper.map(currentUser, FarmerProfileDTO.class);
    }

    @Override
    public void updateCurrentFarmerProfile(FarmerProfileDTO dto) {
        User currentUser = loggedUserHelperService.get();

        currentUser.setFirstName(dto.getFirstName());
        currentUser.setLastName(dto.getLastName());
        currentUser.setLocation(dto.getLocation());
        currentUser.setBio(dto.getBio());
        currentUser.setProfileImageUrl(dto.getProfileImageUrl());

        userRepository.save(currentUser);
    }
}
