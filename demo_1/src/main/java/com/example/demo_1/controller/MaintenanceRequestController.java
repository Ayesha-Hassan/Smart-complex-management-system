package com.example.demo_1.controller;

import com.example.demo_1.dto.MaintenanceRequestDto;
import com.example.demo_1.model.MaintenanceStatus;
import com.example.demo_1.requestObject.CreateMaintenanceRequestRequest;
import com.example.demo_1.requestObject.UpdateMaintenanceRequestRequest;
import com.example.demo_1.service.MaintenanceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance/requests")
@RequiredArgsConstructor
public class MaintenanceRequestController {

    private final MaintenanceRequestService maintenanceRequestService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<MaintenanceRequestDto>> getAllRequests() {
        List<MaintenanceRequestDto> requests = maintenanceRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT', 'TECHNICIAN')")
    public ResponseEntity<MaintenanceRequestDto> getRequestById(@PathVariable Long id) {
        MaintenanceRequestDto request = maintenanceRequestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT', 'TECHNICIAN')")
    public ResponseEntity<List<MaintenanceRequestDto>> getRequestsByResident(@PathVariable Long residentId) {
        List<MaintenanceRequestDto> requests = maintenanceRequestService.getRequestsByResidentId(residentId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<MaintenanceRequestDto>> getRequestsByStatus(@PathVariable MaintenanceStatus status) {
        List<MaintenanceRequestDto> requests = maintenanceRequestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/technician/{technicianId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<List<MaintenanceRequestDto>> getRequestsByTechnician(@PathVariable Long technicianId) {
        List<MaintenanceRequestDto> requests = maintenanceRequestService.getRequestsByTechnicianId(technicianId);
        return ResponseEntity.ok(requests);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<MaintenanceRequestDto> createRequest(@RequestBody CreateMaintenanceRequestRequest request) {
        MaintenanceRequestDto createdRequest = maintenanceRequestService.createRequest(request);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<MaintenanceRequestDto> updateRequest(
            @PathVariable Long id,
            @RequestBody UpdateMaintenanceRequestRequest request) {
        MaintenanceRequestDto updatedRequest = maintenanceRequestService.updateRequest(id, request);
        return ResponseEntity.ok(updatedRequest);
    }

    @PutMapping("/{requestId}/assign/{technicianId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    public ResponseEntity<MaintenanceRequestDto> assignTechnician(
            @PathVariable Long requestId,
            @PathVariable Long technicianId) {
        MaintenanceRequestDto updatedRequest = maintenanceRequestService.assignTechnician(requestId, technicianId);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        maintenanceRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
