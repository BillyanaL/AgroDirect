package com.example.agrodirect.repositories;

import com.example.agrodirect.models.dtos.ReviewViewDTO;
import com.example.agrodirect.models.entities.Product;
import com.example.agrodirect.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByApprovedFalse(); // за админ

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.approved = true")
    Double findAverageRatingByProductId(Long productId);


    List<Review> findAllByProductIdOrderByCreatedOnDesc(Long productId);

    List<Review> findAllByProductAndApprovedTrue(Product product);
}
