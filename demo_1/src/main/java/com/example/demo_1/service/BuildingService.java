package com.example.demo_1.service;

import com.example.demo_1.model.Building;
import com.example.demo_1.repository.BuildingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BuildingService {
    private BuildingRepository buildingRepository;


    /**
     * Find a building by its address.
     */
    public Building getBuildingByAddress(String address) {
        return buildingRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException("Building not found with address: " + address));
    }

    /**
     * Find all buildings with a specific number of total floors or greater.
     */
    public List<Building> getBuildingsByMinimumFloors(Integer minFloors) {
        if (minFloors == null || minFloors <= 0) {
            throw new IllegalArgumentException("Minimum floors must be a positive integer");
        }
        return buildingRepository.findByTotalFloorsGreaterThan(minFloors);
    }

    /**
     * Find all buildings constructed within a specified year range.
     */
    public List<Building> getBuildingsByConstructionYearRange(Integer startYear, Integer endYear) {
        if (startYear == null || endYear == null || startYear > endYear) {
            throw new IllegalArgumentException("Valid year range required: startYear <= endYear");
        }
        return buildingRepository.findByConstructedYearsBetween(startYear, endYear);
    }

    /**
     * Retrieve a specific building by ID.
     */
    public Building getBuildingById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Building not found with ID: " + id));
    }

    /**
     * Retrieve all buildings.
     */
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    /**
     * Create or update a building.
     */
    public Building saveBuilding(Building building) {
        if (building.getAddress() == null || building.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Building address is required");
        }
        return buildingRepository.save(building);
    }

    /**
     * Delete a building by ID.
     */
    public void deleteBuildingById(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new RuntimeException("Building not found with ID: " + id);
        }
        buildingRepository.deleteById(id);
    }
}
