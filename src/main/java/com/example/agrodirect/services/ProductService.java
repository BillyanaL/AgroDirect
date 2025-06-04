package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.models.dtos.UpdateProductDTO;
import com.example.agrodirect.models.entities.Product;

import java.util.List;

public interface ProductService {

    void add(AddProductDTO addProductDTO);
    List<ProductViewDTO> getAll();
    ProductViewDTO mapProductToDTO(Product product);


    void deleteProduct(Long id);

    UpdateProductDTO getProductById(Long id);

    ProductViewDTO getProduct2ById(Long id);

    void update(Long id, UpdateProductDTO updateProductDTO);

    List<ProductViewDTO> getFilteredProducts(String keyword, String category, String sort);

    List<ProductViewDTO> getAllProductsForAdmin();

    void toggleProductActiveStatus(Long productId);

    void softDelete(Long id);
}
