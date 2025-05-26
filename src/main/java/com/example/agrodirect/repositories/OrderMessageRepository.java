package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.OrderMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMessageRepository extends JpaRepository<OrderMessage, Long> {


}
