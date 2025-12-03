package com.example.demo_1.controller;

import com.example.demo_1.dto.ResidentDto;
import com.example.demo_1.requestObject.CreateResidentRequest;
import com.example.demo_1.requestObject.UpdateResidentRequest;
import com.example.demo_1.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<List<ResidentDto>> getAllResidents() {
        List<ResidentDto> residents = residentService.getAllResidents();
        return ResponseEntity.ok(residents);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD', 'RESIDENT')")
    public ResponseEntity<ResidentDto> getResidentById(@PathVariable Long id) {
        ResidentDto resident = residentService.getResidentById(id);
        return ResponseEntity.ok(resident);
    }

    @GetMapping("/building/{buildingId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    public ResponseEntity<List<ResidentDto>> getResidentsByBuilding(@PathVariable Long buildingId) {
        List<ResidentDto> residents = residentService.getResidentsByBuildingId(buildingId);
        return ResponseEntity.ok(residents);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResidentDto> createResident(@RequestBody @jakarta.validation.Valid CreateResidentRequest request) {
        ResidentDto createdResident = residentService.createResident(request);
        return new ResponseEntity<>(createdResident, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResidentDto> updateResident(
            @PathVariable Long id,
            @RequestBody @jakarta.validation.Valid UpdateResidentRequest request) {
        ResidentDto updatedResident = residentService.updateResident(id, request);
        return ResponseEntity.ok(updatedResident);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }
}
