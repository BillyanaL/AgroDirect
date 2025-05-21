package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddToCartDTO;
import com.example.agrodirect.models.dtos.CartItemsViewDTO;
import com.example.agrodirect.models.dtos.UpdateCartItemsDTO;
import com.example.agrodirect.models.entities.User;

import java.util.List;

public interface CartService {

    void addProductToCart(User user, AddToCartDTO addToCartDTO);

    List<CartItemsViewDTO> getCartItemsForUser(User user);

    void updateQuantity(Long cartItemId, UpdateCartItemsDTO updateCartItemsDTO);

    double calculateTotalCartPrice(User user);

    void removeProductFromCart(Long cartItemId);

}
