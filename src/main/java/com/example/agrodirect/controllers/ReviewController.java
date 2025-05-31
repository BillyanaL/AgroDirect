package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.AddReviewDTO;
import com.example.agrodirect.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/product/{id}/review")
    @PreAuthorize("isAuthenticated()")
    public String submitReview(@PathVariable Long id,
                               @Valid @ModelAttribute("reviewDTO") AddReviewDTO reviewDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Моля, попълнете ревюто коректно.");
            return "redirect:/product/" + id;
        }

        reviewDTO.setProductId(id);
        reviewService.addReview(reviewDTO);

        redirectAttributes.addFlashAttribute("success", "Благодарим! Ревюто ви ще бъде публикувано след одобрение.");
        return "redirect:/product/" + id;
    }


    @PostMapping("/articles/{id}/review")
    @PreAuthorize("isAuthenticated()")
    public String submitArticleReview(@PathVariable Long id,
                                      @Valid @ModelAttribute("reviewDTO") AddReviewDTO reviewDTO,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Моля, попълнете коментара коректно.");
            return "redirect:/farmer/articles/details/" + id;

        }

        reviewDTO.setArticleId(id);
        reviewService.addReview(reviewDTO);

        redirectAttributes.addFlashAttribute("success", "Благодарим! Коментарът ви ще бъде публикуван след одобрение.");
        return "redirect:/farmer/articles/details/" + id;

    }



}
