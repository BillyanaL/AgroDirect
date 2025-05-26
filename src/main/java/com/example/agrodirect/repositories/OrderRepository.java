package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Order;
import com.example.agrodirect.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.orderItems oi " +
            "LEFT JOIN FETCH oi.product " +
            "WHERE o.user.id = :userId")
    List<Order> findAllByUserIdWithDetails(@Param("userId") Long userId);



}
