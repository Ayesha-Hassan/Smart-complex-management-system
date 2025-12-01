package com.example.demo_1.service;

import com.example.demo_1.dto.TechnicianDto;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.AvailabilityStatus;
import com.example.demo_1.model.Technician;
import com.example.demo_1.repository.MaintenanceRequestRepository;
import com.example.demo_1.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final MaintenanceRequestRepository maintenanceRequestRepository;

    @Transactional(readOnly = true)
    public List<TechnicianDto> getAllTechnicians() {
        return technicianRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TechnicianDto getTechnicianById(Long id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", id));
        return mapToDto(technician);
    }

    @Transactional(readOnly = true)
    public List<TechnicianDto> getAvailableTechnicians() {
        return technicianRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TechnicianDto> getTechniciansBySpecialization(String specialization) {
        return technicianRepository.findBySpecialization(specialization).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private TechnicianDto mapToDto(Technician technician) {
        TechnicianDto dto = new TechnicianDto();
        dto.setId(technician.getId());
        dto.setSpecialization(technician.getSpecialization() != null ? 
                technician.getSpecialization().name() : "");
        dto.setAvailabilityStatus(technician.getAvailabilityStatus() != null ?
                AvailabilityStatus.AVAILABLE : AvailabilityStatus.BUSY);

        if (technician.getUser() != null) {
            dto.setName(technician.getUser().getName());
            dto.setPhone(technician.getUser().getPhone());
        }

        // Count active tasks
        int activeTasks = maintenanceRequestRepository.findByTechnicianId(technician.getId())
                .stream()
                .filter(req -> req.getStatus() == com.example.demo_1.model.MaintenanceStatus.IN_PROGRESS)
                .collect(Collectors.toList())
                .size();
        dto.setActiveTasksCount(activeTasks);

        return dto;
    }
}
