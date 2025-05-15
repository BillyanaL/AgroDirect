package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;

public interface FarmerProfileService {

    FarmerProfileDTO getCurrentFarmerProfile();
    void updateCurrentFarmerProfile(FarmerProfileDTO dto);
}
