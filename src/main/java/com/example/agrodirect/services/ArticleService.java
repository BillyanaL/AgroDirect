package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.*;

import java.util.List;

public interface ArticleService {

    void create(AddArticleDTO addArticleDTO);

    List<ArticleViewDTO> getAllArticlesByAuthor();

    UpdateArticleDTO getEditDTOById(Long id);

    void updateArticle(Long id, UpdateArticleDTO dto);

    void deleteArticleById(Long id);

    List<ArticleViewDTO> getAllArticlesForAdmin();

    void approveArticle(Long id);

    List<ArticleViewDTO> getAllUnapprovedArticles();

    ArticleDetailsViewDTO getArticleDetailsById(Long id);

    List<ArticleViewDTO> getAllApprovedArticles();


    List<ArticleViewDTO> getLatestTwoApprovedArticles();



}
