package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.models.dtos.UpdateProductDTO;

import java.util.List;

public interface ProductService {

    void add(AddProductDTO addProductDTO);
    List<ProductViewDTO> getAll();

    void deleteProduct(Long id);

    UpdateProductDTO getProductById(Long id);


    void update(Long id, UpdateProductDTO updateProductDTO);
}
