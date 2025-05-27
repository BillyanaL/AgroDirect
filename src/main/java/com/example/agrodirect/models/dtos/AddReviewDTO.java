package com.example.agrodirect.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddReviewDTO {

    @NotBlank(message = "Коментарът не може да е празен.")
    @Size(max = 1000, message = "Коментарът трябва да е до 1000 символа.")
    private String content;

    @Min(value = 1, message = "Минимална оценка е 1.")
    @Max(value = 5, message = "Максимална оценка е 5.")
    private int rating;

    private Long productId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
