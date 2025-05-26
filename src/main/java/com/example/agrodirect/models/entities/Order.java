package com.example.agrodirect.models.entities;

import com.example.agrodirect.models.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private Double totalPrice;


    @Embedded
    private ShippingDetails shippingDetails;

    @Column(nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


    @Column
    private LocalDate orderDate;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<OrderMessage> messages = new ArrayList<>();


    public Order() {}

    public Order(User user, List<OrderItem> orderItems, Double totalPrice, ShippingDetails shippingDetails, String paymentMethod, OrderStatus status, LocalDate orderDate, LocalDate deliveryDate) {
        this.user = user;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.shippingDetails = shippingDetails;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<OrderMessage> messages) {
        this.messages = messages;
    }
}
