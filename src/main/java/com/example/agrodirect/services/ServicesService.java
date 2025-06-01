package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddServiceDTO;
import com.example.agrodirect.models.dtos.ServiceViewDTO;
import com.example.agrodirect.models.dtos.UpdateServiceDTO;

import java.util.List;

public interface ServicesService {

    void createService(AddServiceDTO dto);

    List<ServiceViewDTO> getAllServicesByFarmerId();

    UpdateServiceDTO getUpdateDTOById(Long id);

    void updateService(Long id, UpdateServiceDTO dto);

    void deleteServiceById(Long id);

    List<ServiceViewDTO> getAllUnapprovedServices();

    void approveService(Long id);

    ServiceViewDTO getServiceViewById(Long id);

    List<ServiceViewDTO> getAllApprovedServices();


}
