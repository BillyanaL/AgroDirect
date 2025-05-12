package com.example.agrodirect.services;

import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.enums.CategoryName;

import java.util.Set;

public interface CategoryService {

    Category getByName(CategoryName name);
}
