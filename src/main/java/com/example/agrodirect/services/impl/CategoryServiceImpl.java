package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.enums.CategoryName;
import com.example.agrodirect.repositories.CategoryRepository;
import com.example.agrodirect.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl (CategoryRepository categoryRepository) {

        this.categoryRepository = categoryRepository;
    }

    public Category getByName(CategoryName name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

}
