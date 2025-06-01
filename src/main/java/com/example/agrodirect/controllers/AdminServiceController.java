package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.ServiceViewDTO;
import com.example.agrodirect.services.ServicesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminServiceController {

    private final ServicesService servicesService;

    public AdminServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping("/admin/services/pending")
    public String pendingServices(Model model) {

        List<ServiceViewDTO> allServices = servicesService.getAllUnapprovedServices();

        model.addAttribute("allServices",allServices);
        return "admin-services";
    }

    @PostMapping("/admin/service/approve/{id}")
    public String approveService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicesService.approveService(id);
            redirectAttributes.addFlashAttribute("successMessage", "Услугата беше успешно одобрена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при одобрение: " + e.getMessage());
        }
        return "redirect:/admin/services/pending";
    }

    @PostMapping("/admin/service/delete/{id}")
    public String deleteService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            servicesService.deleteServiceById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Услугата беше успешно изтрита!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изтриване: " + e.getMessage());
        }
        return "redirect:/admin/services/pending";
    }


}
