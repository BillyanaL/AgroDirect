package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.OrderAdminViewDTO;
import com.example.agrodirect.models.enums.OrderStatus;
import com.example.agrodirect.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrdersForAdmin());
        return "admin-orders";
    }

    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Long orderId,
                                    @RequestParam OrderStatus newStatus,
                                    RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(orderId, newStatus);
        redirectAttributes.addFlashAttribute("successMessage", "Статусът на поръчката е обновен.");
        return "redirect:/admin/orders";
    }


}
