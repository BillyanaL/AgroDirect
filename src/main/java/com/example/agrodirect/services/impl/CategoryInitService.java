package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.enums.CategoryName;
import com.example.agrodirect.repositories.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryInitService {

    private final CategoryRepository categoryRepository;

    public CategoryInitService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initCategories() {
        if (categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values()).forEach(enumCategory -> {
                Category category = new Category();
                category.setName(enumCategory);
                categoryRepository.save(category);
            });
        }
    }
}
