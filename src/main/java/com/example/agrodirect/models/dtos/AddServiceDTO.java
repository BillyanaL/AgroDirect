package com.example.agrodirect.models.dtos;

import com.example.agrodirect.models.enums.ServiceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddServiceDTO {

    @NotBlank(message = "Заглавието е задължително.")
    @Size(min = 5, max = 50, message = "Заглавието трябва да е между 5 и 50 символа.")
    private String title;

    @NotBlank(message = "Съдържанието е задължително.")
    @Size(min = 30, message = "Съдържанието трябва да съдържа поне 30 символа.")
    private String description;

    @NotBlank(message = "Въведете URL на изображението.")
    private String imageUrl;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Телефонният номер трябва да бъде в формат xxx-xxx-xxxx")
    private String phoneNumber;

    @NotNull
    private ServiceCategory category;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }


}
