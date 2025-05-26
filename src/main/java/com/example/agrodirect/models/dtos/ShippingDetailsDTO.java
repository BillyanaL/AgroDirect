package com.example.agrodirect.models.dtos;

import com.example.agrodirect.validation.annotations.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShippingDetailsDTO {

    @NotBlank(message = "Името е задължително.")
    @Size(min = 2, message = "Името трябва да бъде дълго поне 2 символа.")
    private String firstName;

    @NotBlank(message = "Фамилията е задължително.")
    @Size(min = 2, message = "Фамилията трябва да бъде дълго поне 2 символа.")
    private String lastName;

    @NotBlank(message = "Имейлът е задължителен.")
    @Email
    private String email;

    @NotBlank(message = "Името на улицата е задължително.")
    private String street;

    @NotBlank(message = "Номерът на улицата е задължителено.")
    private String streetNumber;

    @NotBlank(message = "Имато на града е задължителни.")
    private String city;

    @NotBlank(message = "Името на областта е задължително.")
    private String state;

    @Pattern(regexp = "^[0-9]{4}$", message = "Пощенският код трябва да бъде 4 цифри.")
    private String zip;

    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Телефонният номер трябва да бъде в формат xxx-xxx-xxxx")
    private String phone;

    @NotBlank(message = "Името на държавата е задължително.")
    private String country;

    private String formOrderNotes;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFormOrderNotes() {
        return formOrderNotes;
    }

    public void setFormOrderNotes(String formOrderNotes) {
        this.formOrderNotes = formOrderNotes;
    }
}
