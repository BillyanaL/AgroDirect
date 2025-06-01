package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddServiceDTO;
import com.example.agrodirect.models.dtos.ServiceViewDTO;
import com.example.agrodirect.models.dtos.UpdateServiceDTO;
import com.example.agrodirect.models.entities.Services;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.ServiceRepository;
import com.example.agrodirect.services.ServicesService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {

    private final LoggedUserHelperService loggedUserHelperService;

    private final ServiceRepository serviceRepository;

    public ServicesServiceImpl(LoggedUserHelperService loggedUserHelperService, ServiceRepository serviceRepository) {
        this.loggedUserHelperService = loggedUserHelperService;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void createService(AddServiceDTO dto) {

        Services services = this.mapAddServiceDTOToServices(dto);

        serviceRepository.save(services);

    }

    @Override
    public List<ServiceViewDTO> getAllServicesByFarmerId() {

        User currentFarmer = loggedUserHelperService.get();

        return serviceRepository.findAllByFarmerIdOrderByCreatedOnDesc(currentFarmer.getId())
                .stream()
                .map(this::mapToViewDTO)
                .toList();
    }

    @Override
    public UpdateServiceDTO getUpdateDTOById(Long id) {
        User loggedUser = loggedUserHelperService.get();

        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Услугата не е намерена!"));

        if (!service.getFarmer().getId().equals(loggedUser.getId())) {
            throw new SecurityException("Нямате достъп до тази услуга!");
        }

        UpdateServiceDTO dto = new UpdateServiceDTO();
        dto.setId(service.getId());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setImageUrl(service.getImageUrl());
        dto.setPhoneNumber(service.getPhoneNumber());
        dto.setCategory(service.getCategory());

        return dto;
    }

    @Override
    public void updateService(Long id, UpdateServiceDTO dto) {
        User loggedUser = loggedUserHelperService.get();

        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Услугата не е намерена!"));

        boolean isAdmin = loggedUser.getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals(UserRoles.ADMIN));

        if (!service.getFarmer().getId().equals(loggedUser.getId()) && !isAdmin) {
            throw new SecurityException("Нямате права да редактирате тази услуга!");
        }

        service.setTitle(dto.getTitle());
        service.setDescription(dto.getDescription());
        service.setImageUrl(dto.getImageUrl());
        service.setPhoneNumber(dto.getPhoneNumber());
        service.setCategory(dto.getCategory());
        service.setApproved(false); // след редакция услугата трябва отново да бъде одобрена

        serviceRepository.save(service);
    }

    @Override
    public void deleteServiceById(Long id) {
        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Услугата не е намерена!"));

        User loggedUser = loggedUserHelperService.get();

        boolean isAdmin = loggedUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(UserRoles.ADMIN));

        if (!service.getFarmer().getId().equals(loggedUser.getId()) && !isAdmin) {
            throw new SecurityException("Нямате права да изтриете тази услуга!");
        }

        serviceRepository.delete(service);
    }

    @Override
    public List<ServiceViewDTO> getAllUnapprovedServices() {

        return serviceRepository.findAllByApprovedFalseOrderByCreatedOnDesc()
                .stream()
                .map(this::mapToViewDTO)
                .toList();
    }

    @Override
    public void approveService(Long id) {
        Services service = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Услугата не е намерена!"));

        service.setApproved(true);
        serviceRepository.save(service);
    }

    @Override
    public ServiceViewDTO getServiceViewById(Long id) {
        return serviceRepository.findById(id)
                .map(this::mapToViewDTO)
                .orElseThrow(() -> new IllegalArgumentException("Услугата не е намерена!"));
    }

    @Override
    public List<ServiceViewDTO> getAllApprovedServices() {
        return serviceRepository.findAllByApprovedTrue()
                .stream()
                .map(this::mapToViewDTO)
                .toList();
    }




    private ServiceViewDTO mapToViewDTO(Services service) {
        ServiceViewDTO dto = new ServiceViewDTO();
        dto.setId(service.getId());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setImageUrl(service.getImageUrl());
        dto.setPhoneNumber(service.getPhoneNumber());
        dto.setCategory(service.getCategory());
        dto.setApproved(service.isApproved());
        dto.setCreatedOn(service.getCreatedOn());

        User farmer = service.getFarmer();
        dto.setFarmerName(farmer.getFirstName() + " " + farmer.getLastName());
        dto.setFarmerId(farmer.getId());

        return dto;
    }


    private Services mapAddServiceDTOToServices(AddServiceDTO addServiceDTO){

        Services services = new Services();
        services.setTitle(addServiceDTO.getTitle());
        services.setImageUrl(addServiceDTO.getImageUrl());
        services.setDescription(addServiceDTO.getDescription());
        services.setPhoneNumber(addServiceDTO.getPhoneNumber());
        services.setCategory(addServiceDTO.getCategory());

        User farmer = loggedUserHelperService.get();
        services.setFarmer(farmer);
        services.setCreatedOn(LocalDateTime.now());
        services.setApproved(false);

        return services;
    }


}
