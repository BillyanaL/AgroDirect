package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByFarmer_Id(Long farmerId);

}
