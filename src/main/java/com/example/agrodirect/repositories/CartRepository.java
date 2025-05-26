package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Cart;
import com.example.agrodirect.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>  {

    Optional<Cart> findByUser(User user);

    Optional<Cart> findByUserId(Long userId);


}
