package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.models.dtos.FarmerProfileDetailsViewDTO;

import java.util.List;

public interface FarmerService {

    FarmerProfileDTO getCurrentFarmerProfile();
    void updateCurrentFarmerProfile(FarmerProfileDTO dto);

    FarmerProfileDetailsViewDTO getFarmerProfileById(Long id);

    List<FarmerProfileDetailsViewDTO> getAllFarmers();
}
