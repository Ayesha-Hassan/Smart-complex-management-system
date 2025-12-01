package com.example.demo_1.service;

import com.example.demo_1.model.SecurityGuard;
import com.example.demo_1.repository.SecurityGuardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@Service
public class SecurityGuardService {
    private SecurityGuardRepository securityGuardRepository;

    /**
     * Retrieve all security guards sorted by name.
     */
    public List<SecurityGuard> getAllGuardsSortedByName() {
        return securityGuardRepository.findAllOrderedByName();
    }

    /**
     * Retrieve all security guards whose shift starts at a specific time,
     * sorted by name.
     */
    public List<SecurityGuard> getGuardsByShiftStartTime(Time shiftStartTime) {
        return securityGuardRepository.findGuardsByShiftStart(shiftStartTime);
    }

    /**
     * Retrieve all security guards whose shift ends at a specific time,
     * sorted by name.
     */
    public List<SecurityGuard> getGuardsByShiftEndTime(Time shiftEndTime) {
        return securityGuardRepository.findGuardsByShiftEnd(shiftEndTime);
    }

    /**
     * Retrieve all security guards with shift starting between two times,
     * sorted by name.
     */
    public List<SecurityGuard> getGuardsByShiftStartTimeBetween(Time startTime, Time endTime) {
        return securityGuardRepository.findGuardsByShiftStartBetween(startTime, endTime);
    }

    /**
     * Retrieve all security guards with shift ending between two times,
     * sorted by name.
     */
    public List<SecurityGuard> getGuardsByShiftEndTimeBetween(Time startTime, Time endTime) {
        return securityGuardRepository.findGuardsByShiftEndBetween(startTime, endTime);
    }

    /**
     * Retrieve a specific security guard by ID.
     */
    public SecurityGuard getGuardById(Long id) {
        return securityGuardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Security Guard not found with ID: " + id));
    }

    /**
     * Retrieve a security guard by name.
     */
    public SecurityGuard getGuardByName(String name) {
        return securityGuardRepository.findGuardByName(name)
                .orElseThrow(() -> new RuntimeException("Security Guard not found with name: " + name));
    }

    /**
     * Retrieve all security guards.
     */
    public List<SecurityGuard> getAllGuards() {
        return securityGuardRepository.findAll();
    }

    /**
     * Create or update a security guard.
     */
    public SecurityGuard saveGuard(SecurityGuard guard) {
        return securityGuardRepository.save(guard);
    }

    /**
     * Delete a security guard by ID.
     */
    public void deleteGuardById(Long id) {
        securityGuardRepository.deleteById(id);
    }
}
