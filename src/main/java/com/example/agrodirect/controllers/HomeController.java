package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.FarmerProfileDetailsViewDTO;
import com.example.agrodirect.models.dtos.ServiceViewDTO;
import com.example.agrodirect.services.FarmerService;
import com.example.agrodirect.services.ServicesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final FarmerService farmerService;
    private final ServicesService servicesService;

    public HomeController(FarmerService farmerService, ServicesService servicesService) {
        this.farmerService = farmerService;
        this.servicesService = servicesService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        List<FarmerProfileDetailsViewDTO> farmers = farmerService.getAllFarmers();
        model.addAttribute("farmers", farmers);
        List<ServiceViewDTO> allApprovedServices = servicesService.getAllApprovedServices();
        model.addAttribute("allServices", allApprovedServices);
        return "about";
    }

}
