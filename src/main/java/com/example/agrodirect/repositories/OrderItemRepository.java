package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.OrderItem;
import com.example.agrodirect.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>  {

    List<OrderItem> findByProductFarmer(User farmer);
}
