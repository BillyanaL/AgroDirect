package com.example.agrodirect.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddArticleDTO {

    @NotBlank(message = "Заглавието е задължително.")
    @Size(min = 5, max = 100, message = "Заглавието трябва да бъде между 5 и 100 символа.")
    private String title;

    @NotBlank(message = "Съдържанието е задължително.")
    @Size(min = 30, message = "Съдържанието трябва да съдържа поне 30 символа.")
    private String content;

    @NotBlank(message = "Въведете URL на изображението.")
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
