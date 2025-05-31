package com.example.agrodirect.services.impl;

import com.example.agrodirect.models.dtos.AddReviewDTO;
import com.example.agrodirect.models.dtos.ReviewViewDTO;
import com.example.agrodirect.models.entities.Review;
import com.example.agrodirect.models.entities.User;
import com.example.agrodirect.models.enums.UserRoles;
import com.example.agrodirect.repositories.ArticleRepository;
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

    private final ArticleRepository articleRepository;

    public ReviewServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository, LoggedUserHelperService loggedUserHelperService, ArticleRepository articleRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.loggedUserHelperService = loggedUserHelperService;
        this.articleRepository = articleRepository;
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
    public double getAverageRatingForArticle(Long articleId) {
        Double average = reviewRepository.findAverageRatingByArticleId(articleId);
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
        List<Review> reviews = reviewRepository.findAllByProductIdOrderByCreatedOnDesc(productId);
        return filterAndMapReviews(reviews);
    }

    @Override
    public List<ReviewViewDTO> getAllReviewViewsByArticleId(Long articleId) {
        List<Review> reviews = reviewRepository.findAllByArticleIdOrderByCreatedOnDesc(articleId);
        return filterAndMapReviews(reviews);
    }

    private List<ReviewViewDTO> filterAndMapReviews(List<Review> reviews) {
        User currentUser = loggedUserHelperService.get();
        Long loggedUserId = currentUser.getId();

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals(UserRoles.ADMIN));

        return reviews.stream()
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
        dto.setRating(review.getRating());
        dto.setContent(review.getContent());
        dto.setCreatedOn(review.getCreatedOn());
        dto.setApproved(review.isApproved());

        if (review.getProduct() != null) {
            dto.setProductName(review.getProduct().getName());
            dto.setProductId(review.getProduct().getId());
        }

        if (review.getArticle() != null) {
            dto.setArticleTitle(review.getArticle().getTitle());
            dto.setArticleId(review.getArticle().getId());
        }

        return dto;
    }

    private Review mapAddReviewDTtoToReview(AddReviewDTO addReviewDTO) {
        User currentUser = loggedUserHelperService.get();

        Review review = new Review();
        review.setUser(currentUser);
        review.setRating(addReviewDTO.getRating());
        review.setContent(addReviewDTO.getContent());
        review.setCreatedOn(LocalDateTime.now());
        review.setApproved(false);

        if (addReviewDTO.getProductId() != null) {
            review.setProduct(productRepository.findById(addReviewDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Продуктът не е намерен.")));
        } else if (addReviewDTO.getArticleId() != null) {
            review.setArticle(articleRepository.findById(addReviewDTO.getArticleId())
                    .orElseThrow(() -> new IllegalArgumentException("Статията не е намерена.")));
        } else {
            throw new IllegalArgumentException("Липсва продукт или статия за ревюто.");
        }

        return review;
    }


}
