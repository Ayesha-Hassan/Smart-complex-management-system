package com.example.demo_1.service;

import com.example.demo_1.dto.MaintenanceRequestDto;
import com.example.demo_1.exception.BadRequestException;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.MaintenanceRequest;
import com.example.demo_1.model.MaintenanceStatus;
import com.example.demo_1.model.Resident;
import com.example.demo_1.model.Technician;
import com.example.demo_1.repository.MaintenanceRequestRepository;
import com.example.demo_1.repository.ResidentRepository;
import com.example.demo_1.repository.TechnicianRepository;
import com.example.demo_1.requestObject.CreateMaintenanceRequestRequest;
import com.example.demo_1.requestObject.UpdateMaintenanceRequestRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaintenanceRequestService {

    private  MaintenanceRequestRepository maintenanceRequestRepository;
    private  ResidentRepository residentRepository;
    private  TechnicianRepository technicianRepository;

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDto> getAllRequests() {
        return maintenanceRequestRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaintenanceRequestDto getRequestById(Long id) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", "id", id));
        return mapToDto(request);
    }

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDto> getRequestsByResidentId(Long residentId) {
        return maintenanceRequestRepository.findByResidentId(residentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDto> getRequestsByStatus(MaintenanceStatus status) {
        return maintenanceRequestRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaintenanceRequestDto> getRequestsByTechnicianId(Long technicianId) {
        return maintenanceRequestRepository.findByTechnicianId(technicianId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MaintenanceRequestDto createRequest(CreateMaintenanceRequestRequest request) {
        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Resident", "id", request.getResidentId()));

        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setResident(resident);
        maintenanceRequest.setMaintenanceType(request.getMaintenanceType());
        maintenanceRequest.setDescription(request.getDescription());
        maintenanceRequest.setPriority(request.getPriority());
        maintenanceRequest.setStatus(MaintenanceStatus.PENDING);
        maintenanceRequest.setRequestDate(new Date());

        MaintenanceRequest savedRequest = maintenanceRequestRepository.save(maintenanceRequest);
        return mapToDto(savedRequest);
    }

    @Transactional
    public MaintenanceRequestDto updateRequest(Long id, UpdateMaintenanceRequestRequest request) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", "id", id));

        if (request.getStatus() != null) {
            maintenanceRequest.setStatus(request.getStatus());
            
            // If status is completed, set completed date
            if (request.getStatus() == MaintenanceStatus.COMPLETED) {
                maintenanceRequest.setCompleted_date(new Date());
            }
        }

        if (request.getPriority() != null) {
            maintenanceRequest.setPriority(request.getPriority());
        }

        if (request.getDescription() != null) {
            maintenanceRequest.setDescription(request.getDescription());
        }

        MaintenanceRequest updatedRequest = maintenanceRequestRepository.save(maintenanceRequest);
        return mapToDto(updatedRequest);
    }

    @Transactional
    public MaintenanceRequestDto assignTechnician(Long requestId, Long technicianId) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", "id", requestId));

        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", technicianId));

        // Check if maintenance type matches technician specialization
        if (technician.getSpecialization() != maintenanceRequest.getMaintenanceType()) {
            throw new BadRequestException("Technician specialization does not match maintenance type");
        }

        maintenanceRequest.setTechnician(technician);
        maintenanceRequest.setStatus(MaintenanceStatus.IN_PROGRESS);

        MaintenanceRequest updatedRequest = maintenanceRequestRepository.save(maintenanceRequest);
        return mapToDto(updatedRequest);
    }

    @Transactional
    public void deleteRequest(Long id) {
        MaintenanceRequest request = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", "id", id));
        maintenanceRequestRepository.delete(request);
    }

    private MaintenanceRequestDto mapToDto(MaintenanceRequest request) {
        MaintenanceRequestDto dto = new MaintenanceRequestDto();
        dto.setId(request.getId());
        dto.setMaintenanceType(request.getMaintenanceType());
        dto.setDescription(request.getDescription());
        dto.setStatus(request.getStatus());
        dto.setPriority(request.getPriority());
        dto.setRequestDate(request.getRequestDate());
        dto.setCompletedDate(request.getCompleted_date());

        if (request.getResident() != null) {
            dto.setResidentId(request.getResident().getId());
            if (request.getResident().getUser() != null) {
                dto.setResidentName(request.getResident().getUser().getName());
            }
        }

        if (request.getTechnician() != null) {
            dto.setTechnicianId(request.getTechnician().getId());
            if (request.getTechnician().getUser() != null) {
                dto.setTechnicianName(request.getTechnician().getUser().getName());
            }
        }

        return dto;
    }
}
