package com.example.agrodirect.models.dtos;

import com.example.agrodirect.models.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class FarmerProfileDetailsViewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String location;
    private String bio;
    private String profileImageUrl;

    private List<ProductViewDTO> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public List<ProductViewDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewDTO> products) {
        this.products = products;
    }
}
