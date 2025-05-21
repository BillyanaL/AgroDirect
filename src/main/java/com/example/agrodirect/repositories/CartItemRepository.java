package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Cart;
import com.example.agrodirect.models.entities.CartItem;
import com.example.agrodirect.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProduct(Cart cart, Product product);

    Optional<CartItem> findById(Long id);
}
