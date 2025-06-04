package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.models.dtos.FarmerProfileDetailsViewDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.UserRepository;
import com.example.agrodirect.services.FarmerService;
import com.example.agrodirect.services.ProductService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FarmerServiceImpl implements FarmerService {
    private final LoggedUserHelperService loggedUserHelperService;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FarmerServiceImpl(LoggedUserHelperService loggedUserHelperService, ProductService productService, UserRepository userRepository, ModelMapper modelMapper) {
        this.loggedUserHelperService = loggedUserHelperService;
        this.productService = productService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FarmerProfileDTO getCurrentFarmerProfile() {
        User currentUser = loggedUserHelperService.get();
//        return modelMapper.map(currentUser, FarmerProfileDTO.class);

        return mapToFarmerProfileDTO(currentUser);
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

    @Override
    public FarmerProfileDetailsViewDTO getFarmerProfileById(Long id) {
        User farmer = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фермерът не е намерен!"));

//        return modelMapper.map(farmer, FarmerProfileDetailsViewDTO.class);
        return mapFarmerToDTO(farmer);
    }

    @Override
    public List<FarmerProfileDetailsViewDTO> getAllFarmers() {

//        List<User> farmers = userRepository.findByRoles_Name(UserRoles.FARMER);
//
//        return farmers.stream()
//                .map(farmer -> modelMapper.map(farmer, FarmerProfileDetailsViewDTO.class))
//                .collect(Collectors.toList());

        List<User> farmers = userRepository.findByRoles_Name(UserRoles.FARMER);
        return farmers.stream()
                .map(this::mapFarmerToDTO)
                .toList();
    }

    private FarmerProfileDetailsViewDTO mapFarmerToDTO(User farmer) {
        FarmerProfileDetailsViewDTO dto = new FarmerProfileDetailsViewDTO();

        dto.setId(farmer.getId());
        dto.setFirstName(farmer.getFirstName());
        dto.setLastName(farmer.getLastName());
        dto.setLocation(farmer.getLocation());
        dto.setBio(farmer.getBio());
        dto.setProfileImageUrl(farmer.getProfileImageUrl());

        List<ProductViewDTO> productDTOs = farmer.getProducts()
                .stream()
                .map(productService::mapProductToDTO)
                .toList();

        dto.setProducts(productDTOs);

        return dto;
    }

    private FarmerProfileDTO mapToFarmerProfileDTO(User user) {
        FarmerProfileDTO dto = new FarmerProfileDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setLocation(user.getLocation());
        dto.setBio(user.getBio());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        return dto;
    }




}
