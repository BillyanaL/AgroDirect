package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.*;
import com.example.agrodirect.models.enums.CategoryName;
import com.example.agrodirect.services.ProductService;
import com.example.agrodirect.services.ReviewService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ProductController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final ProductService productService;
    private final ReviewService reviewService;

    private final LoggedUserHelperService loggedUserHelperService;

    public ProductController(ProductService productService, ReviewService reviewService, LoggedUserHelperService loggedUserHelperService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.loggedUserHelperService = loggedUserHelperService;
    }

    @GetMapping("/farmer/products/my")
    public ModelAndView myProducts(Model model,  @ModelAttribute("successMessage") String successMessage) {
        model.addAttribute("products", productService.getAll());

        if (successMessage != null && !successMessage.isBlank()) {
            model.addAttribute("successMessage", successMessage);
        }

        return new ModelAndView("my-products");
    }

    @GetMapping("/farmer/products/add")
    public ModelAndView add(Model model) {
        if (!model.containsAttribute("addProductDTO")) {
            model.addAttribute("addProductDTO", new AddProductDTO());
        }

        return new ModelAndView("add-product");
    }

    @PostMapping("/farmer/products/add")
    public ModelAndView add(@Valid AddProductDTO addProductDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            final String attributeName = "addProductDTO";
            redirectAttributes
                    .addFlashAttribute(attributeName, addProductDTO)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            modelAndView.setViewName("redirect:/farmer/products/add");
        } else {
            productService.add(addProductDTO);
            modelAndView.setViewName("redirect:/farmer/products/my");
        }

        return modelAndView;
    }

    @GetMapping("/farmer/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id,  RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Продуктът беше успешно изтрит.");
        return "redirect:/farmer/products/my";
    }

    @GetMapping("/farmer/products/edit/{id}")
    public ModelAndView updateProduct(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("updateProductDTO")) {
            model.addAttribute("updateProductDTO", productService.getProductById(id));
        }

        model.addAttribute("productId", id);

        return new ModelAndView("edit-product");
    }

    @PostMapping("/farmer/products/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid UpdateProductDTO updateProductDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateProductDTO", updateProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateProductDTO", bindingResult);
            return "redirect:/farmer/products/edit/" + id;
        }

        productService.update(id, updateProductDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Продуктът е успешно обновен!");
        return "redirect:/farmer/products/my";
    }

    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable Long id, Model model) {

        ProductViewDTO product = productService.getProduct2ById(id);

        model.addAttribute("product", product);

        model.addAttribute("reviews", reviewService.getAllReviewViewsByProductId(id));
        model.addAttribute("loggedUser", loggedUserHelperService.get());

        double avgRating = reviewService.getAverageRatingForProduct(id);
        String formattedRating = String.format("%.1f", avgRating);

        model.addAttribute("averageRating", formattedRating);

        model.addAttribute("reviewDTO", new AddReviewDTO());
        return "product-details";
    }

//    @GetMapping("/products")
//    public String showAllProducts(@RequestParam(required = false) String sort, Model model) {
////        List<ProductViewDTO> products = productService.getAllProducts();
//
//        List<ProductViewDTO> products = productService.getAllSorted(sort);
//
//        model.addAttribute("products", products);
//        model.addAttribute("totalProductCount", products.size());
//
//        return "products";
//    }

    @GetMapping("/products")
    public String showAllProducts(@RequestParam(required = false) String sort,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) String category,
                                  Model model) {

        List<ProductViewDTO> products = productService.getFilteredProducts(keyword, category, sort);

        model.addAttribute("products", products);
        model.addAttribute("totalProductCount", products.size());
        model.addAttribute("categories", CategoryName.values());

        model.addAttribute("selectedSort", sort);
        model.addAttribute("selectedKeyword", keyword);
        model.addAttribute("selectedCategory", category);

        return "products";
    }





}
