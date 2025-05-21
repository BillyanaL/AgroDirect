package com.example.agrodirect.models.dtos;

import jakarta.validation.constraints.Min;

public class AddToCartDTO {

    private Long productId;
    @Min(value = 1, message = "Количеството трябва да бъде по-голямо или равно на 1.")
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
