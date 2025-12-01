package com.example.demo_1.service;

import com.example.demo_1.dto.ResidentDto;
import com.example.demo_1.exception.BadRequestException;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.Apartment;
import com.example.demo_1.model.Resident;
import com.example.demo_1.model.Status;
import com.example.demo_1.model.User;
import com.example.demo_1.repository.ApartmentRepository;
import com.example.demo_1.repository.ResidentRepository;
import com.example.demo_1.repository.UserRepository;
import com.example.demo_1.requestObject.CreateResidentRequest;
import com.example.demo_1.requestObject.UpdateResidentRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidentService {

    private ResidentRepository residentRepository;
    private ApartmentRepository apartmentRepository;
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ResidentDto> getAllResidents() {
        return residentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResidentDto getResidentById(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resident", "id", id));
        return mapToDto(resident);
    }

    @Transactional(readOnly = true)
    public List<ResidentDto> getResidentsByBuildingId(Long buildingId) {
        List<Resident> residents = residentRepository.findByApartmentBuildingId(buildingId);
        return residents.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResidentDto createResident(CreateResidentRequest request) {
        // Validate apartment exists
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Apartment", "id", request.getApartmentId()));

        // Check if apartment is available
        if (apartment.getStatus() != Status.Vacant) {
            throw new BadRequestException("Apartment is not available for new residents");
        }

        Resident resident = new Resident();
        
        // Link to user if userId is provided
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));
            resident.setUser(user);
        }
        
        resident.setApartment(apartment);
        resident.setMove_in_date(new Date());

        Resident savedResident = residentRepository.save(resident);
        
        // Update apartment status to occupied
        apartment.setStatus(Status.Occupied);
        apartmentRepository.save(apartment);

        return mapToDto(savedResident);
    }

    @Transactional
    public ResidentDto updateResident(Long id, UpdateResidentRequest request) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resident", "id", id));

        // Update apartment if changed
        if (request.getApartmentId() != null && !request.getApartmentId().equals(resident.getApartment().getId())) {
            // Mark old apartment as available
            Apartment oldApartment = resident.getApartment();
            oldApartment.setStatus(Status.Vacant);
            apartmentRepository.save(oldApartment);

            // Assign new apartment
            Apartment newApartment = apartmentRepository.findById(request.getApartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Apartment", "id", request.getApartmentId()));
            
            if (newApartment.getStatus() != Status.Vacant) {
                throw new BadRequestException("New apartment is not available");
            }
            
            resident.setApartment(newApartment);
            newApartment.setStatus(Status.Occupied);
            apartmentRepository.save(newApartment);
        }

        Resident updatedResident = residentRepository.save(resident);
        return mapToDto(updatedResident);
    }

    @Transactional
    public void deleteResident(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resident", "id", id));

        // Mark apartment as available
        if (resident.getApartment() != null) {
            Apartment apartment = resident.getApartment();
            apartment.setStatus(Status.Vacant);
            apartmentRepository.save(apartment);
        }

        // Set move out date
        resident.setMove_out_date(new Date());
        residentRepository.save(resident);
        
        // Or actually delete: residentRepository.delete(resident);
    }

    private ResidentDto mapToDto(Resident resident) {
        ResidentDto dto = new ResidentDto();
        dto.setId(resident.getId());
        
        if (resident.getUser() != null) {
            dto.setName(resident.getUser().getName());
            dto.setEmail(resident.getUser().getEmail());
            dto.setPhone(resident.getUser().getPhone());
        }
        
        if (resident.getApartment() != null) {
            dto.setApartmentId(resident.getApartment().getId());
            dto.setApartmentNumber(resident.getApartment().getUnitNumber());
            
            if (resident.getApartment().getBuilding() != null) {
            }
        }
        
        return dto;
    }
}
