package com.example.agrodirect.controllers;

import com.example.agrodirect.services.ReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reviews")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {


    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/pending")
    public String showPendingReviews(Model model) {
        model.addAttribute("pendingReviews", reviewService.getPendingReviewDTOs());
        return "admin-pending-reviews";
    }

    @PostMapping("/{id}/approve")
    public String approveReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.approveReview(id);
        redirectAttributes.addFlashAttribute("success", "Ревюто беше одобрено успешно.");
        return "redirect:/admin/reviews/pending";
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewService.deleteReview(id);
        redirectAttributes.addFlashAttribute("success", "Ревюто беше изтрито успешно.");
        return "redirect:/admin/reviews/pending";
    }
}
