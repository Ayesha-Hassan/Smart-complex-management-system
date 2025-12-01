package com.example.demo_1.controller;

import com.example.demo_1.dto.TechnicianDto;
import com.example.demo_1.service.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technicians")
@RequiredArgsConstructor
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<TechnicianDto>> getAllTechnicians() {
        List<TechnicianDto> technicians = technicianService.getAllTechnicians();
        return ResponseEntity.ok(technicians);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<TechnicianDto> getTechnicianById(@PathVariable Long id) {
        TechnicianDto technician = technicianService.getTechnicianById(id);
        return ResponseEntity.ok(technician);
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<TechnicianDto>> getAvailableTechnicians() {
        List<TechnicianDto> technicians = technicianService.getAvailableTechnicians();
        return ResponseEntity.ok(technicians);
    }

    @GetMapping("/specialization/{specialization}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<TechnicianDto>> getTechniciansBySpecialization(@PathVariable String specialization) {
        List<TechnicianDto> technicians = technicianService.getTechniciansBySpecialization(specialization);
        return ResponseEntity.ok(technicians);
    }
}
