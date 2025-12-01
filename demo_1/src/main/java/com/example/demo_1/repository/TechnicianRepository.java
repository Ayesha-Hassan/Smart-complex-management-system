package com.example.demo_1.repository;

import com.example.demo_1.model.AvailabilityStatus;
import com.example.demo_1.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    List<Technician> findBySpecialization(String specialization);
    List<Technician> findByAvailabilityStatus(AvailabilityStatus status);
}
