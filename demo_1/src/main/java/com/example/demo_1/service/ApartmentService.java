package com.example.demo_1.service;

import com.example.demo_1.model.Apartment;
import com.example.demo_1.model.Status;
import com.example.demo_1.repository.ApartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ApartmentService {
    private ApartmentRepository apartmentRepository;

    public List<Apartment> allOccupiedInBuilding(Long id, Status status){
        return apartmentRepository.findByBuildingIdAndStatus(id,status);
    }
}
