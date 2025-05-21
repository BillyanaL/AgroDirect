package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.models.dtos.UpdateProductDTO;
import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.repositories.ProductRepository;
import com.example.agrodirect.services.CategoryService;
import com.example.agrodirect.services.ProductService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @Override
    public List<ProductViewDTO> getAll() {

        Long farmerId = loggedUserHelperService.get().getId();

        return productRepository.findAllByFarmer_Id(farmerId)
                .stream()
                .map(this::mapProductToDTO)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен"));

        productRepository.delete(product);
    }

    @Override
    public UpdateProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен"));

        return new UpdateProductDTO()
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .setCategory(product.getCategory().getName())
                .setImageUrl(product.getImageUrl());
    }

    @Override
    public ProductViewDTO getProduct2ById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен"));


        return this.mapProductToDTO(product);
    }


    @Override
    public void update(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен"));

        map(dto, product);

        Category category = categoryService.getByName(dto.getCategory());
        product.setCategory(category);

        productRepository.save(product);
    }

    private static void map(UpdateProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
    }

    private ProductViewDTO mapProductToDTO(Product product){
        return new ProductViewDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory().getName(),
                product.getImageUrl()
        );
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

        User currentUser = loggedUserHelperService.get();
        product.setFarmer(currentUser);
        return product;
    }


}
