package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByAuthorIdOrderByCreatedOnDesc(Long authorId);

    List<Article> findAllByOrderByCreatedOnDesc();

    List<Article> findAllByApprovedFalseOrderByCreatedOnDesc();

    List<Article> findTop6ByApprovedTrueAndIdNotOrderByCreatedOnDesc(Long id);

    List<Article> findAllByApprovedTrueOrderByCreatedOnDesc();



}
