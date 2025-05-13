package com.example.agrodirect.models.dtos;

import com.example.agrodirect.models.enums.CategoryName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProductDTO {

    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UpdateProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public UpdateProductDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public UpdateProductDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public CategoryName getCategory() {
        return category;
    }

    public UpdateProductDTO setCategory(CategoryName category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UpdateProductDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
