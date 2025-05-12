package com.example.agrodirect.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
    @Column(name = "image_url",nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private User farmer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getFarmer() {
        return farmer;
    }

    public Product setFarmer(User farmer) {
        this.farmer = farmer;
        return this;
    }
}
