package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.ProductRepository;
import com.example.agrodirect.services.CategoryService;
import com.example.agrodirect.services.ProductService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final LoggedUserHelperService loggedUserHelperService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(CategoryService categoryService, LoggedUserHelperService loggedUserHelperService, ProductRepository productRepository) {
        this.categoryService = categoryService;
        this.loggedUserHelperService = loggedUserHelperService;
        this.productRepository = productRepository;
    }

    @Override
    public void add(AddProductDTO addProductDTO) {

        Product product = this.mapAddProductDTOToProduct(addProductDTO);

        productRepository.save(product);

    }

    private Product mapAddProductDTOToProduct(AddProductDTO addProductDTO) {

        Product product = new Product();
        product.setName(addProductDTO.getName());
        product.setDescription(addProductDTO.getDescription());
        product.setPrice(addProductDTO.getPrice());
        product.setQuantity(addProductDTO.getQuantity());
        product.setImageUrl(addProductDTO.getImageUrl());

        Category category = categoryService.getByName(addProductDTO.getCategory());
        product.setCategory(category);

        User currentUser = loggedUserHelperService.get(); // текущо логнатият фермер
        product.setFarmer(currentUser);
        return product;
    }


}
