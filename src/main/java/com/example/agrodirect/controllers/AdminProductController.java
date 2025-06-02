package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.ProductViewDTO;
import com.example.agrodirect.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProductsForAdmin());
        return "admin-products";
    }

    @PostMapping("/admin/products/toggle-status")
    public String toggleProductStatus(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        productService.toggleProductActiveStatus(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Статусът беше променен.");
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/delete")
    public String deleteProduct(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        productService.softDelete(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Продуктът беше изтрит.");
        return "redirect:/admin/products";
    }


    @GetMapping("/admin/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductViewDTO product = productService.getProduct2ById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

}
