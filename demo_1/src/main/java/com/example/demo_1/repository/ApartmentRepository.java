package com.example.demo_1.repository;

import com.example.demo_1.model.Apartment;
import com.example.demo_1.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
    List<Apartment> findByBuildingIdAndStatus(Long building_id, Status status);

}
