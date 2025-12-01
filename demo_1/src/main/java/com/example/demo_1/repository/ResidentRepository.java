package com.example.demo_1.repository;

import com.example.demo_1.model.Resident;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    public List<Resident> findByApartmentBuildingId(Long id);
}
