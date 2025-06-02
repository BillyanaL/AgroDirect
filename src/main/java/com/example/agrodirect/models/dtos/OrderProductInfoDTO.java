package com.example.agrodirect.models.dtos;

import com.example.agrodirect.models.enums.OrderStatus;

public class OrderProductInfoDTO {

    private String productName;
    private String farmerName;
    private int quantity;
    private double price;
    private OrderStatus status;

    public OrderProductInfoDTO() {
    }

    public OrderProductInfoDTO(String productName, String farmerName, int quantity, double price, OrderStatus status) {
        this.productName = productName;
        this.farmerName = farmerName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
