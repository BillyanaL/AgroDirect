package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.*;
import com.example.agrodirect.models.entities.Article;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.ArticleRepository;
import com.example.agrodirect.repositories.ReviewRepository;
import com.example.agrodirect.services.ArticleService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final LoggedUserHelperService loggedUserHelperService;

    private final ArticleRepository articleRepository;

    private final ReviewRepository reviewRepository;

    public ArticleServiceImpl(LoggedUserHelperService loggedUserHelperService, ArticleRepository articleRepository, ReviewRepository reviewRepository) {
        this.loggedUserHelperService = loggedUserHelperService;
        this.articleRepository = articleRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void create(AddArticleDTO addArticleDTO) {

        Article article = this.mapAddArticleDTOToArticle(addArticleDTO);

        articleRepository.save(article);

    }

    @Override
    public List<ArticleViewDTO> getAllArticlesByAuthor() {

        User currentUser = loggedUserHelperService.get();

        return articleRepository.findAllByAuthorIdOrderByCreatedOnDesc(currentUser.getId())
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();

    }

    @Override
    public UpdateArticleDTO getEditDTOById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена."));

        UpdateArticleDTO dto = new UpdateArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setImageUrl(article.getImageUrl());

        return dto;
    }

    @Override
    public void updateArticle(Long id, UpdateArticleDTO dto) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена."));

        User currentUser = loggedUserHelperService.get();

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals(UserRoles.ADMIN));

        if (!article.getAuthor().getId().equals(currentUser.getId()) && !isAdmin) {
            throw new SecurityException("Нямате права да редактирате тази статия.");
        }

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setImageUrl(dto.getImageUrl());
        article.setUpdatedOn(LocalDateTime.now());

        if (!isAdmin) {
            article.setApproved(false);
        }

        articleRepository.save(article);
    }


    @Override
    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена!"));

        User loggedUser = loggedUserHelperService.get();

        boolean isAuthor = article.getAuthor().getId().equals(loggedUser.getId());
        boolean isAdmin = loggedUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName() == UserRoles.ADMIN);


        if (!isAuthor && !isAdmin) {
            throw new SecurityException("Нямате права да изтриете тази статия!");
        }

        reviewRepository.deleteAllByArticleId(id);

        articleRepository.delete(article);
    }

    @Override
    public List<ArticleViewDTO> getAllArticlesForAdmin() {
        return articleRepository.findAllByOrderByCreatedOnDesc()
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();
    }

    @Override
    public List<ArticleViewDTO> getAllUnapprovedArticles() {
        return articleRepository.findAllByApprovedFalseOrderByCreatedOnDesc()
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();
    }


    @Override
    public void approveArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена."));

        article.setApproved(true);
        article.setUpdatedOn(LocalDateTime.now());

        articleRepository.save(article);
    }

    @Override
    public ArticleDetailsViewDTO getArticleDetailsById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена"));

        List<ArticleViewDTO> latestArticles = articleRepository
                .findTop6ByApprovedTrueAndIdNotOrderByCreatedOnDesc(id)
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();

        ArticleDetailsViewDTO dto = new ArticleDetailsViewDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setImageUrl(article.getImageUrl());
        dto.setAuthorFullName(article.getAuthor().getFirstName() + " " + article.getAuthor().getLastName());
        dto.setCreatedOn(article.getCreatedOn());
        dto.setLatestArticles(latestArticles);

        return dto;
    }

    public List<ArticleViewDTO> getAllApprovedArticles() {
        return articleRepository.findAllByApprovedTrueOrderByCreatedOnDesc()
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();
    }

    @Override
    public List<ArticleViewDTO> getLatestTwoApprovedArticles() {
        return articleRepository.findTop2ByApprovedTrueOrderByCreatedOnDesc()
                .stream()
                .map(this::mapToArticleViewDTO)
                .toList();
    }



    private ArticleViewDTO mapToArticleViewDTO(Article article) {
        ArticleViewDTO dto = new ArticleViewDTO();

        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setImageUrl(article.getImageUrl());
        dto.setApproved(article.isApproved());
        dto.setCreatedOn(article.getCreatedOn());
        dto.setAuthorId(article.getAuthor().getId());

        String fullName = article.getAuthor().getFirstName() + " " + article.getAuthor().getLastName();
        dto.setAuthorFullName(fullName);

        return dto;
    }


    private Article mapAddArticleDTOToArticle(AddArticleDTO addArticleDTO){

        Article article = new Article();
        article.setTitle(addArticleDTO.getTitle());
        article.setContent(addArticleDTO.getContent());
        article.setImageUrl(addArticleDTO.getImageUrl());

        User author = loggedUserHelperService.get();
        article.setAuthor(author);
        article.setCreatedOn(LocalDateTime.now());
        article.setApproved(false);

        return article;

    }


}
