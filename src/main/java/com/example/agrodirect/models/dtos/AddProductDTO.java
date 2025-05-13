package com.example.agrodirect.models.dtos;

import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.CategoryName;
import jakarta.validation.constraints.*;

public class AddProductDTO {
    @NotBlank(message = "Името е задължително.")
    @Size(min = 2, max = 50, message = "Името трябва да е между 2 и 50 символа.")
    private String name;

    @NotBlank(message = "Описанието е задължително.")
    @Size(min = 10, max = 500, message = "Описанието трябва да е между 10 и 500 символа.")
    private String description;

    @DecimalMin(value = "0.01", message = "Цената трябва да е положително число.")
    private Double price;

    @Min(value = 1, message = "Минималното количество е 1.")
    private Integer quantity;

    private CategoryName category;
    @NotBlank(message = "URL към изображението е задължителен.")
    private String imageUrl;

    private User author;

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

    public CategoryName getCategory() {
        return category;
    }

    public AddProductDTO setCategory(CategoryName category) {
        this.category = category;
        return this;

    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
