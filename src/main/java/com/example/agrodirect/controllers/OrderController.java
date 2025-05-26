package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.AddOrderDTO;
import com.example.agrodirect.models.dtos.CartItemsViewDTO;
import com.example.agrodirect.models.entities.Order;
import com.example.agrodirect.models.entities.OrderItem;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.OrderStatus;
import com.example.agrodirect.repositories.OrderItemRepository;
import com.example.agrodirect.repositories.OrderRepository;
import com.example.agrodirect.services.CartService;
import com.example.agrodirect.services.OrderService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final LoggedUserHelperService loggedUserHelperService;

    private final CartService cartService;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    public OrderController(OrderService orderService, LoggedUserHelperService loggedUserHelperService, CartService cartService, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderService = orderService;
        this.loggedUserHelperService = loggedUserHelperService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        User user = loggedUserHelperService.get();

        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setUserId(user.getId());

        List<CartItemsViewDTO> cartItems = cartService.getCartItemsForUser(user);

        addOrderDTO.setCartItems(cartItems);

        double totalCartPrice = cartService.calculateTotalCartPrice(user);

        model.addAttribute("addOrderDTO", addOrderDTO);
        model.addAttribute("cartItems", cartService.getCartItemsForUser(user));
        model.addAttribute("totalCartPrice", totalCartPrice);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@Valid @ModelAttribute("addOrderDTO") AddOrderDTO addOrderDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            System.out.println("Има грешки");

            bindingResult.getAllErrors().forEach(error ->
                    System.out.println(error.toString())
            );

            return "checkout";
        }

        try {
            Order order = orderService.createOrder(addOrderDTO);
            redirectAttributes.addFlashAttribute("order", order);
            return "redirect:/my-orders";
        } catch (Exception e) {
            System.out.println("Тук съм!!");
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/checkout";
        }
    }

    @GetMapping("/my-orders")
    public String showMyOrders(Model model) {
        User user = loggedUserHelperService.get();

        List<Order> orders = orderRepository.findAllByUserIdWithDetails(user.getId());

        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @GetMapping("/farmer/orders")
    public String showFarmerOrders(Model model) {
        User farmer = loggedUserHelperService.get(); // логнат фермер
        List<OrderItem> farmerOrderItems = orderItemRepository.findByProductFarmer(farmer);
        model.addAttribute("farmerOrderItems", farmerOrderItems);
        return "farmer-orders";
    }


    @PostMapping("/farmer/update-status")
    public String updateStatus(@RequestParam Long orderItemId,
                               @RequestParam OrderStatus status,
                               RedirectAttributes redirectAttributes) {

        User loggedFarmer = loggedUserHelperService.get();

        try {
            orderService.updateOrderItemStatus(orderItemId, status, loggedFarmer.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Статусът е обновен успешно!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Грешка при обновяване на статус: " + ex.getMessage());
        }

        return "redirect:/farmer/orders";
    }








}
