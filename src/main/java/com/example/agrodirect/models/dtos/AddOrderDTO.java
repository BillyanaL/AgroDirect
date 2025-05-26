package com.example.agrodirect.models.dtos;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

public class AddOrderDTO {

    private Long userId;
    private List<CartItemsViewDTO> cartItems = new ArrayList<>();
    private Double totalPrice;
    @Valid
    private ShippingDetailsDTO shippingDetails = new ShippingDetailsDTO();
    private String paymentMethod;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemsViewDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemsViewDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShippingDetailsDTO getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetailsDTO shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
