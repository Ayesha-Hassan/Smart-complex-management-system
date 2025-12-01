package com.example.demo_1.repository;

import com.example.demo_1.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {


    /**
     * Find a building by address.
     * Uses named query: Building.findByAddress
     */
    Optional<Building> findByAddress(String address);

    /**
     * Find buildings with total floors greater than a specified minimum.
     * Uses named query: Building.findByTotalFloorsGreaterThan
     */
    List<Building> findByTotalFloorsGreaterThan(Integer minFloors);

    /**
     * Find buildings constructed within a specified year range.
     * Uses named query: Building.findByConstructedYearsRange
     */
    List<Building> findByConstructedYearsBetween(Integer startYear, Integer endYear);
}
