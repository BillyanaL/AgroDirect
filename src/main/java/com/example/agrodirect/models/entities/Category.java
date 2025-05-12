package com.example.agrodirect.models.entities;

import com.example.agrodirect.models.enums.CategoryName;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryName name;

    @OneToMany(targetEntity = Product.class, mappedBy = "category")
    private List<Product> products;

    public Category() {
        this.products = new ArrayList<>();
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
