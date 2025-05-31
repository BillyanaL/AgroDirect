package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.ArticleViewDTO;
import com.example.agrodirect.models.dtos.UpdateArticleDTO;
import com.example.agrodirect.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/articles")
public class AdminArticleController {

    private final ArticleService articleService;

    public AdminArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String allArticles(Model model) {
        List<ArticleViewDTO> allArticles = articleService.getAllUnapprovedArticles();
        model.addAttribute("allArticles", allArticles);
        return "admin-articles";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        articleService.approveArticle(id);
        redirectAttributes.addFlashAttribute("successMessage", "Статията беше одобрена.");
        return "redirect:/admin/articles";
    }



    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        articleService.deleteArticleById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Статията беше изтрита успешно.");
        return "redirect:/admin/articles";
    }
}
