package com.example.agrodirect.models.dtos;

import jakarta.validation.constraints.Min;

public class UpdateCartItemsDTO {

    private Long cartItemId;
    @Min(value = 1, message = "Невалидно количество!")
    private int quantity;

    private double price;

    private double totalPrice;


    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
