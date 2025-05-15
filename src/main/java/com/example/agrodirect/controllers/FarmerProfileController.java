package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.models.dtos.FarmerProfileDetailsViewDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.services.FarmerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FarmerProfileController {

    private final FarmerService farmerService;


    public FarmerProfileController(FarmerService farmerProfileService) {
        this.farmerService = farmerProfileService;
    }

    @GetMapping("/farmer/profile")
    public String showProfile(Model model, @ModelAttribute("successMessage") String successMessage) {
        if (!model.containsAttribute("farmerProfileDTO")) {
            model.addAttribute("farmerProfileDTO", farmerService.getCurrentFarmerProfile());
        }

        if (successMessage != null && !successMessage.isBlank()) {
            model.addAttribute("successMessage", successMessage);
        }
        return "farmer-profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("farmerProfileDTO") FarmerProfileDTO dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("farmerProfileDTO", dto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.farmerProfileDTO", bindingResult);
            return "redirect:/farmer/profile";
        }

        farmerService.updateCurrentFarmerProfile(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Профилът е успешно обновен!");
        return "redirect:/farmer/profile";
    }

    @GetMapping("/farmers/{id}")
    public ModelAndView viewFarmerProfile(@PathVariable Long id) {

        FarmerProfileDetailsViewDTO farmerDTO = farmerService.getFarmerProfileById(id);
        List<ProductViewDTO> products = farmerDTO.getProducts();

        ModelAndView modelAndView = new ModelAndView("project-details");
        modelAndView.addObject("farmerProfileDetailsViewDTO", farmerDTO);
        modelAndView.addObject("products", products);

        return modelAndView;
    }

    @GetMapping("/farmers")
    public String viewFarmers(Model model) {
        List<FarmerProfileDetailsViewDTO> farmers = farmerService.getAllFarmers();
        model.addAttribute("farmers", farmers);
        return "farmers";
    }
}
