package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddReviewDTO;
import com.example.agrodirect.models.dtos.ReviewViewDTO;

import java.util.List;

public interface ReviewService {

    void addReview(AddReviewDTO dto);
    List<ReviewViewDTO> getAllReviewViewsByProductId(Long productId);

    List<ReviewViewDTO> getAllReviewViewsByArticleId(Long articleId);

    double getAverageRatingForProduct(Long productId);

    double getAverageRatingForArticle(Long articleId);


    //For admin:
    List<ReviewViewDTO> getPendingReviewDTOs();
    void approveReview(Long id);

    void deleteReview(Long id);

}
