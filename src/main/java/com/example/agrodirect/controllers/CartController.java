package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.AddToCartDTO;
import com.example.agrodirect.models.dtos.CartItemsViewDTO;
import com.example.agrodirect.models.dtos.UpdateCartItemsDTO;
import com.example.agrodirect.models.entities.Cart;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.CartRepository;
import com.example.agrodirect.services.CartService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final LoggedUserHelperService loggedUserHelperService;

    private final CartRepository cartRepository;


    public CartController(CartService cartService, LoggedUserHelperService loggedUserHelperService, CartRepository cartRepository) {
        this.cartService = cartService;
        this.loggedUserHelperService = loggedUserHelperService;
        this.cartRepository = cartRepository;
    }


    @PostMapping("/add")
    public String addProductToCart(@Valid @ModelAttribute AddToCartDTO addToCartDTO, RedirectAttributes redirectAttributes) {

        User user = loggedUserHelperService.get();

        try {
            cartService.addProductToCart(user, addToCartDTO);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/product/" + addToCartDTO.getProductId();
        }

        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        User user = loggedUserHelperService.get();
        List<CartItemsViewDTO> cartItems = cartService.getCartItemsForUser(user);

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        double totalCartPrice = cartService.calculateTotalCartPrice(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalCartPrice", totalCartPrice);

        return "cart";
    }

    @PostMapping("/updateQuantity")
    public String updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {

        try {
            UpdateCartItemsDTO updateCartItemsDTO = new UpdateCartItemsDTO();
            updateCartItemsDTO.setQuantity(quantity);


            cartService.updateQuantity(cartItemId, updateCartItemsDTO);

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/removeProduct")
    public String removeProductFromCart(@RequestParam Long cartItemId, RedirectAttributes redirectAttributes) {
        try {
            cartService.removeProductFromCart(cartItemId);

            redirectAttributes.addFlashAttribute("successMessage", "Продуктът беше успешно премахнат от количката!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/cart";
    }



}
