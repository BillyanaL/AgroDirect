package com.example.agrodirect.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDetailsViewDTO {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    private String authorFullName;
    private LocalDateTime createdOn;

    private List<ArticleViewDTO> latestArticles; // За sidebar


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<ArticleViewDTO> getLatestArticles() {
        return latestArticles;
    }

    public void setLatestArticles(List<ArticleViewDTO> latestArticles) {
        this.latestArticles = latestArticles;
    }
}
