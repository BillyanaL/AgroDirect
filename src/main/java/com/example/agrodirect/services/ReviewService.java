package com.example.agrodirect.services;

import com.example.agrodirect.models.dtos.AddReviewDTO;
import com.example.agrodirect.models.dtos.ReviewViewDTO;

import java.util.List;

public interface ReviewService {

    void addReview(AddReviewDTO dto);
    List<ReviewViewDTO> getAllReviewViewsByProductId(Long productId);

    double getAverageRatingForProduct(Long productId);

    //For admin:
    List<ReviewViewDTO> getPendingReviewDTOs();
    void approveReview(Long id);

    void deleteReview(Long id);
}
