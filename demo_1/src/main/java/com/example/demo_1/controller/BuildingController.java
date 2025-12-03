package com.example.demo_1.controller;

import com.example.demo_1.model.Building;
import com.example.demo_1.service.BuildingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buildings")
@AllArgsConstructor
public class BuildingController {
    private BuildingService buildingService;


    /**
     * Get all buildings.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Building>> getAllBuildings() {
        List<Building> buildings = buildingService.getAllBuildings();
        return ResponseEntity.ok(buildings);
    }

    /**
     * Get a specific building by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long id) {
        Building building = buildingService.getBuildingById(id);
        return ResponseEntity.ok(building);
    }

//    /**
//     * Search buildings by name keyword (case-insensitive partial match).
//     */
//    @GetMapping("/search")
//    public ResponseEntity<List<Building>> searchBuildingsByName(
//            @RequestParam(name = "keyword") String nameKeyword) {
//        try {
//            List<Building> buildings = buildingService.searchBuildingsByName(nameKeyword);
//            return ResponseEntity.ok(buildings);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    /**
     * Get a building by address.
     */
    @GetMapping("/by-address")
    public ResponseEntity<Building> getBuildingByAddress(
            @RequestParam(name = "address") String address) {
        Building building = buildingService.getBuildingByAddress(address);
        return ResponseEntity.ok(building);
    }

    /**
     * Get all buildings with at least the specified number of floors.
     */
    @GetMapping("/min-floors")
    public ResponseEntity<List<Building>> getBuildingsByMinimumFloors(
            @RequestParam(name = "minFloors") Integer minFloors) {
        try {
            List<Building> buildings = buildingService.getBuildingsByMinimumFloors(minFloors);
            return ResponseEntity.ok(buildings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all buildings constructed within a specified year range.
     */
    @GetMapping("/construction-year-range")
    public ResponseEntity<List<Building>> getBuildingsByConstructionYearRange(
            @RequestParam(name = "startYear") Integer startYear,
            @RequestParam(name = "endYear") Integer endYear) {
        try {
            List<Building> buildings = buildingService.getBuildingsByConstructionYearRange(startYear, endYear);
            return ResponseEntity.ok(buildings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Create a new building.
     */
    @PostMapping("/new")
    public ResponseEntity<Building> createBuilding(@RequestBody @jakarta.validation.Valid Building building) {
        try {
            Building savedBuilding = buildingService.saveBuilding(building);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBuilding);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Update an existing building.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(
            @PathVariable Long id,
            @RequestBody @jakarta.validation.Valid Building buildingDetails) {
        Building existingBuilding = buildingService.getBuildingById(id);

        if (buildingDetails.getAddress() != null && !buildingDetails.getAddress().trim().isEmpty()) {
            existingBuilding.setAddress(buildingDetails.getAddress());
        }
        if (buildingDetails.getTotalFloors() != null) {
            existingBuilding.setTotalFloors(buildingDetails.getTotalFloors());
        }
        if (buildingDetails.getConstructedYears() != null) {
            existingBuilding.setConstructedYears(buildingDetails.getConstructedYears());
        }

        Building updatedBuilding = buildingService.saveBuilding(existingBuilding);
        return ResponseEntity.ok(updatedBuilding);
    }

    /**
     * Delete a building by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        buildingService.deleteBuildingById(id);
        return ResponseEntity.noContent().build();
    }
}
