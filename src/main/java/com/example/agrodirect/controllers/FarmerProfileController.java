package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.FarmerProfileDTO;
import com.example.agrodirect.services.FarmerProfileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;

    public FarmerProfileController(FarmerProfileService farmerProfileService) {
        this.farmerProfileService = farmerProfileService;
    }

    @GetMapping("/farmer/profile")
    public String showProfile(Model model, @ModelAttribute("successMessage") String successMessage) {
        if (!model.containsAttribute("farmerProfileDTO")) {
            model.addAttribute("farmerProfileDTO", farmerProfileService.getCurrentFarmerProfile());
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

        farmerProfileService.updateCurrentFarmerProfile(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Профилът е успешно обновен!");
        return "redirect:/farmer/profile";
    }
}
