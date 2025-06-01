package com.example.agrodirect.repositories;

import com.example.agrodirect.models.entities.Services;
import com.example.agrodirect.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Services, Long>  {

    List<Services> findAllByFarmerIdOrderByCreatedOnDesc(Long farmerId);

    List<Services> findAllByApprovedFalseOrderByCreatedOnDesc();

    List<Services> findAllByApprovedTrue();



}
