package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.models.dtos.UpdateProductDTO;
import com.example.agrodirect.models.entities.Review;
import com.example.agrodirect.models.entities.Category;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.CategoryName;
import com.example.agrodirect.repositories.ProductRepository;
import com.example.agrodirect.repositories.ReviewRepository;
import com.example.agrodirect.services.CategoryService;
import com.example.agrodirect.services.ProductService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final LoggedUserHelperService loggedUserHelperService;
    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    public ProductServiceImpl(CategoryService categoryService, LoggedUserHelperService loggedUserHelperService, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.categoryService = categoryService;
        this.loggedUserHelperService = loggedUserHelperService;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
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

    @Override
    public List<ProductViewDTO> getFilteredProducts(String keyword, String category, String sort) {
        CategoryName categoryName = null;
        if (category != null && !category.isBlank()) {
            try {
                categoryName = CategoryName.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                return List.of();
            }
        }

        CategoryName finalCategoryName = categoryName;

        List<ProductViewDTO> products = productRepository.findAll().stream()
                .filter(p -> keyword == null || keyword.isBlank() || p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .filter(p -> finalCategoryName == null || p.getCategory().getName() == finalCategoryName)
                .map(this::mapProductToDTO)
                .sorted(getComparator(sort))
                .toList();

        return products;
    }

    private Comparator<ProductViewDTO> getComparator(String sort) {

        if (sort == null) {
            return (a, b) -> 0;
        }

        return switch (sort) {
            case "priceAsc" -> Comparator.comparing(ProductViewDTO::getPrice);
            case "priceDesc" -> Comparator.comparing(ProductViewDTO::getPrice).reversed();
            case "ratingAsc" -> Comparator.comparing(ProductViewDTO::getAverageRating, Comparator.nullsLast(Double::compareTo));
            case "ratingDesc" -> Comparator.comparing(ProductViewDTO::getAverageRating, Comparator.nullsLast(Double::compareTo)).reversed();
            default -> (a, b) -> 0; // without sorting
        };
    }


    private static void map(UpdateProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setImageUrl(dto.getImageUrl());
    }

    private ProductViewDTO mapProductToDTO(Product product){

        List<Review> approvedReviews = reviewRepository.findAllByProductAndApprovedTrue(product);

        double avgRating = approvedReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        return new ProductViewDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory().getName(),
                product.getImageUrl(),
                avgRating

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
