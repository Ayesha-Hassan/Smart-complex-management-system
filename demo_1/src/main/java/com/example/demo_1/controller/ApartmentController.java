package com.example.demo_1.controller;

import com.example.demo_1.model.Apartment;
import com.example.demo_1.model.Status;
import com.example.demo_1.service.ApartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/apartment")
public class ApartmentController {
    private ApartmentService apartmentService;

    @GetMapping("/occupancy/{id}")
    public List<Apartment> occupencyIn(@PathVariable(name = "id") Long id){
        return apartmentService.allOccupiedInBuilding(id, Status.Occupied );
    }
}
