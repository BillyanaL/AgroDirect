package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.UserViewDTO;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/users")
    public String showUsersPage(Model model) {
        List<UserViewDTO> farmers = userService.getUsersByRole(UserRoles.FARMER);
        List<UserViewDTO> customers = userService.getUsersByRole(UserRoles.USER);

        model.addAttribute("farmers", farmers);
        model.addAttribute("customers", customers);
        return "admin-users";
    }

    @PostMapping("/admin/users/delete")
    public String softDeleteUser(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.markUserAsDeleted(userId);
            redirectAttributes.addFlashAttribute("successMessage", "Потребителят беше маркиран като изтрит.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при изтриване: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }


    @PostMapping("/admin/users/toggle-status")
    public String toggleUserStatus(@RequestParam Long userId, RedirectAttributes redirectAttributes) {
        userService.toggleUserStatus(userId);
        redirectAttributes.addFlashAttribute("successMessage", "Потребителят беше успешно обновен.");
        return "redirect:/admin/users";
    }


}
