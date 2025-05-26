package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddToCartDTO;
import com.example.agrodirect.models.dtos.CartItemsViewDTO;
import com.example.agrodirect.models.dtos.UpdateCartItemsDTO;
import com.example.agrodirect.models.entities.Cart;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.CartItem;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.CartItemRepository;
import com.example.agrodirect.repositories.CartRepository;
import com.example.agrodirect.repositories.ProductRepository;
import com.example.agrodirect.services.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void addProductToCart(User user, AddToCartDTO addToCartDTO) {

        Cart cart = cartRepository.findByUser(user).orElse(new Cart());
        if (cart.getUser() == null) {
            cart.setUser(user);
            cartRepository.save(cart);
        }

        Product product = productRepository.findById(addToCartDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (addToCartDTO.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Не достатъчно количество на продукта в склада.");
        }

        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + addToCartDTO.getQuantity());

            existingItem.setPrice(product.getPrice());

            cartItemRepository.save(existingItem);
        } else {

            Double totalPrice = product.getPrice() * addToCartDTO.getQuantity();
            CartItem cartItem = new CartItem(cart, product, addToCartDTO.getQuantity(), product.getPrice(),totalPrice);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItemsViewDTO> getCartItemsForUser(User user) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        List<CartItemsViewDTO> cartItemDTOs = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            CartItemsViewDTO cartItemDTO = mapCartItemToDTO(cartItem);

            cartItemDTOs.add(cartItemDTO);
        }

        return cartItemDTOs;
    }

    public void updateQuantity(Long cartItemId, UpdateCartItemsDTO updateCartItemsDTO) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        Product product = cartItem.getProduct();

        if (updateCartItemsDTO.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Не достатъчно количество на продукта в склада.");
        }

        cartItem.setQuantity(updateCartItemsDTO.getQuantity());

        double totalPrice = cartItem.getPrice() * updateCartItemsDTO.getQuantity();
        cartItem.setTotalPrice(totalPrice);

        cartItemRepository.save(cartItem);

        calculateTotalCartPrice(cartItem.getCart().getUser());
    }

    @Override
    public double calculateTotalCartPrice(User user) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        double totalPrice = 0.0;

        for (CartItem cartItem : cart.getItems()) {
            totalPrice += cartItem.getTotalPrice();
        }

        return totalPrice;
    }

    @Override
    public void removeProductFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        cartItemRepository.delete(cartItem);

        calculateTotalCartPrice(cartItem.getCart().getUser());

    }

    @Override
    @Transactional
    public void clearCart(User user) {
        cartRepository.findByUser(user).ifPresent(cart -> {
            cartItemRepository.deleteAllByCartId(cart.getId());
            System.out.println("Количката за потребител " + user.getEmail() + " е изчистена.");
        });
    }

    private static CartItemsViewDTO mapCartItemToDTO(CartItem cartItem) {

        if (cartItem.getProduct() == null) {
            throw new IllegalArgumentException("Product cannot be null for CartItem with ID: " + cartItem.getId());
        }

        CartItemsViewDTO cartItemDTO = new CartItemsViewDTO();
        cartItemDTO.setCartItemId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setProductName(cartItem.getProduct().getName());
        cartItemDTO.setProductImageUrl(cartItem.getProduct().getImageUrl());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setTotalPrice(cartItem.getQuantity() * cartItem.getPrice());

        return cartItemDTO;
    }



}
