package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddReviewDTO;
import com.example.agrodirect.models.dtos.ReviewViewDTO;
import com.example.agrodirect.models.entities.Review;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.ProductRepository;
import com.example.agrodirect.repositories.ReviewRepository;
import com.example.agrodirect.services.ReviewService;
import com.example.agrodirect.services.helpers.LoggedUserHelperService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    private final LoggedUserHelperService loggedUserHelperService;

    public ReviewServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository, LoggedUserHelperService loggedUserHelperService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.loggedUserHelperService = loggedUserHelperService;
    }

    @Override
    public void addReview(AddReviewDTO dto) {

        Review review = this.mapAddReviewDTtoToReview(dto);

        reviewRepository.save(review);
    }

    @Override
    public double getAverageRatingForProduct(Long productId) {
        Double average = reviewRepository.findAverageRatingByProductId(productId);
        return average != null ? average : 0.0;
    }

    @Override
    public List<ReviewViewDTO> getPendingReviewDTOs() {
        return reviewRepository.findAllByApprovedFalse()
                .stream()
                .map(this::mapReviewToDTO)
                .toList();
    }

    @Override
    public void approveReview(Long id) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        review.setApproved(true);
        reviewRepository.save(review);

    }

    @Override
    public void deleteReview(Long id) {

        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewViewDTO> getAllReviewViewsByProductId(Long productId) {

        User currentUser = loggedUserHelperService.get();
        Long loggedUserId = currentUser.getId();

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(UserRoles.ADMIN));

        return reviewRepository.findAllByProductIdOrderByCreatedOnDesc(productId)
                .stream()
                .filter(review ->
                        isAdmin || review.isApproved() || review.getUser().getId().equals(loggedUserId))
                .map(this::mapReviewToDTO)
                .toList();
    }

    private ReviewViewDTO mapReviewToDTO(Review review) {
        ReviewViewDTO dto = new ReviewViewDTO();

        User user = review.getUser();
        String fullName = user.getFirstName() + " " + user.getLastName();

        dto.setId(review.getId());
        dto.setUserId(user.getId());
        dto.setFullName(fullName);
        dto.setProductName(review.getProduct().getName());
        dto.setRating(review.getRating());
        dto.setContent(review.getContent());
        dto.setCreatedOn(review.getCreatedOn());
        dto.setApproved(review.isApproved());
        return dto;
    }


    private Review mapAddReviewDTtoToReview(AddReviewDTO addReviewDTO){

        User currentUser = loggedUserHelperService.get();

        Review review = new Review();
        review.setUser(currentUser);
        review.setProduct(productRepository.findById(addReviewDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен.")));
        review.setRating(addReviewDTO.getRating());
        review.setContent(addReviewDTO.getContent());
        review.setCreatedOn(LocalDateTime.now());
        review.setApproved(false);

        return review;
    }


}
