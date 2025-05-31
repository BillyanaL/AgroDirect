package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.*;
import com.example.agrodirect.services.ArticleService;
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
@RequestMapping("/farmer")
public class ArticleController {

    private final LoggedUserHelperService loggedUserHelperService;

    private final ArticleService articleService;

    private final ReviewService reviewService;

    public ArticleController(LoggedUserHelperService loggedUserHelperService, ArticleService articleService, ReviewService reviewService) {
        this.loggedUserHelperService = loggedUserHelperService;
        this.articleService = articleService;
        this.reviewService = reviewService;
    }


    @GetMapping("/article/add")
    public ModelAndView add(Model model) {
        if (!model.containsAttribute("addArticleDTO")) {
            model.addAttribute("addArticleDTO", new AddArticleDTO());
        }

        return new ModelAndView("article-create");
    }

    @PostMapping("/article/add")
    public String createArticle(@Valid @ModelAttribute("addArticleDTO") AddArticleDTO dto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("addArticleDTO", dto);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "addArticleDTO", bindingResult);
            return "article-create";
        }

        articleService.create(dto);

        redirectAttributes.addFlashAttribute("successMessage", "Статията беше създадена успешно и чака одобрение.");
        return "redirect:/farmer/articles/my";
    }

    @GetMapping("/articles/my")
    public String myArticles(Model model) {

        List<ArticleViewDTO> myArticles = articleService.getAllArticlesByAuthor();
        model.addAttribute("myArticles", myArticles);
        return "my-articles";

    }


    @GetMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable Long id, Model model) {
        UpdateArticleDTO dto = articleService.getEditDTOById(id);
        model.addAttribute("editArticleDTO", dto);
        model.addAttribute("fromAdmin", false);
        return "edit-article";
    }

    @PostMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable Long id,
                             @Valid @ModelAttribute("editArticleDTO") UpdateArticleDTO dto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "edit-article";
        }

        articleService.updateArticle(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Статията беше успешно обновена.");
        return "redirect:/farmer/articles/my";
    }

    @PostMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            articleService.deleteArticleById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Статията беше успешно изтрита.");
        } catch (SecurityException se) {
            redirectAttributes.addFlashAttribute("errorMessage", "Нямате права да изтриете тази статия.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Възникна грешка при изтриване.");
        }

        return "redirect:/farmer/articles/my";
    }

    @GetMapping("/articles/details/{id}")
    public String viewArticleDetails(@PathVariable Long id, Model model) {

        ArticleDetailsViewDTO article = articleService.getArticleDetailsById(id);

        model.addAttribute("article", article);
        model.addAttribute("reviews", reviewService.getAllReviewViewsByArticleId(id));
        model.addAttribute("averageRating", reviewService.getAverageRatingForArticle(id));
        model.addAttribute("loggedUser", loggedUserHelperService.get());

        model.addAttribute("reviewDTO", new AddReviewDTO()); // за формата
        return "blog-details";
    }

    @GetMapping("/articles/all")
    public String viewAllArticles(Model model) {
        List<ArticleViewDTO> articles = articleService.getAllApprovedArticles();
        model.addAttribute("articles", articles);
        return "blog";
    }











}
