package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.AddProductDTO;
import com.example.agrodirect.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

}
