package com.example.agrodirect.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FarmerProfileDTO {

    @NotBlank(message = "Името е задължителна")
    private String firstName;
    @NotBlank(message = "Фамилията е задължителна")
    private String lastName;
    @NotBlank(message = "Локацията е задължителна")
    private String location;

    @Size(max = 1000, message = "Описанието е твърде дълго")
    private String bio;

    @NotBlank(message = "Добавете линк към снимка")
    private String profileImageUrl;


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
}
