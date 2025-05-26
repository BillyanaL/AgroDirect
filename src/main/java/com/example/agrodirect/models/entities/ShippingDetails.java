package com.example.agrodirect.models.entities;


import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String streetNumber;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String country;

    private String formOrderNotes;

    public ShippingDetails() {
    }

    public ShippingDetails(String firstName, String lastName, String email, String street, String streetNumber, String city, String state, String zip, String phone, String country, String formOrderNotes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.country = country;
        this.formOrderNotes = formOrderNotes;
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
