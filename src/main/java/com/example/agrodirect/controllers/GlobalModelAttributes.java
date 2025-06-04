package com.example.agrodirect.controllers;

import com.example.agrodirect.models.dtos.ArticleViewDTO;
import com.example.agrodirect.services.ArticleService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalModelAttributes {

    private final ArticleService articleService;

    public GlobalModelAttributes(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("latestArticles")
    public List<ArticleViewDTO> addLatestArticlesToModel() {
        return articleService.getLatestTwoApprovedArticles();
    }
}
