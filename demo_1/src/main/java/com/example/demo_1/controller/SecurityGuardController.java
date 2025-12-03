package com.example.demo_1.controller;

import com.example.demo_1.model.SecurityGuard;
import com.example.demo_1.service.SecurityGuardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/guards")
@AllArgsConstructor
public class SecurityGuardController {
    private SecurityGuardService securityGuardService;

    /**
     * Get all security guards sorted by name.
     */
    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<SecurityGuard>> getAllGuardsSortedByName() {
        List<SecurityGuard> guards = securityGuardService.getAllGuardsSortedByName();
        return ResponseEntity.ok(guards);
    }

    /**
     * Get all security guards.
     */
    @GetMapping("/all")
    public ResponseEntity<List<SecurityGuard>> getAllGuards() {
        List<SecurityGuard> guards = securityGuardService.getAllGuards();
        return ResponseEntity.ok(guards);
    }

    /**
     * Get a specific security guard by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecurityGuard> getGuardById(@PathVariable Long id) {
        SecurityGuard guard = securityGuardService.getGuardById(id);
        return ResponseEntity.ok(guard);
    }

    /**
     * Get all security guards whose shift starts at a specific time.
     * Query parameter format: HH:MM:SS
     */
    @GetMapping("/shift-start")
    public ResponseEntity<List<SecurityGuard>> getGuardsByShiftStartTime(
            @RequestParam(name = "time") String timeStr) {
        try {
            Time shiftStartTime = Time.valueOf(timeStr);
            List<SecurityGuard> guards = securityGuardService.getGuardsByShiftStartTime(shiftStartTime);
            return ResponseEntity.ok(guards);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all security guards whose shift ends at a specific time.
     * Query parameter format: HH:MM:SS
     */
    @GetMapping("/shift-end")
    public ResponseEntity<List<SecurityGuard>> getGuardsByShiftEndTime(
            @RequestParam(name = "time") String timeStr) {
        try {
            Time shiftEndTime = Time.valueOf(timeStr);
            List<SecurityGuard> guards = securityGuardService.getGuardsByShiftEndTime(shiftEndTime);
            return ResponseEntity.ok(guards);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all security guards with shift starting between two times.
     * Query parameter format: HH:MM:SS
     */
    @GetMapping("/shift-start-between")
    public ResponseEntity<List<SecurityGuard>> getGuardsByShiftStartBetween(
            @RequestParam(name = "startTime") String startTimeStr,
            @RequestParam(name = "endTime") String endTimeStr) {
        try {
            Time startTime = Time.valueOf(startTimeStr);
            Time endTime = Time.valueOf(endTimeStr);
            List<SecurityGuard> guards = securityGuardService.getGuardsByShiftStartTimeBetween(startTime, endTime);
            return ResponseEntity.ok(guards);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all security guards with shift ending between two times.
     * Query parameter format: HH:MM:SS
     */
    @GetMapping("/shift-end-between")
    public ResponseEntity<List<SecurityGuard>> getGuardsByShiftEndBetween(
            @RequestParam(name = "startTime") String startTimeStr,
            @RequestParam(name = "endTime") String endTimeStr) {
        try {
            Time startTime = Time.valueOf(startTimeStr);
            Time endTime = Time.valueOf(endTimeStr);
            List<SecurityGuard> guards = securityGuardService.getGuardsByShiftEndTimeBetween(startTime, endTime);
            return ResponseEntity.ok(guards);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get a security guard by name.
     */
    @GetMapping("/by-name/{name}")
    public ResponseEntity<SecurityGuard> getGuardByName(@PathVariable String name) {
        SecurityGuard guard = securityGuardService.getGuardByName(name);
        return ResponseEntity.ok(guard);
    }

    /**
     * Create a new security guard.
     */
    @PostMapping("/new")
    public ResponseEntity<SecurityGuard> createGuard(@RequestBody @jakarta.validation.Valid SecurityGuard guard) {
        SecurityGuard savedGuard = securityGuardService.saveGuard(guard);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGuard);
    }

    /**
     * Update an existing security guard.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecurityGuard> updateGuard(@PathVariable Long id, @RequestBody @jakarta.validation.Valid SecurityGuard guardDetails) {
        SecurityGuard existingGuard = securityGuardService.getGuardById(id);
        if (guardDetails.getName() != null) {
            existingGuard.setName(guardDetails.getName());
        }
        if (guardDetails.getUser() != null) {
            existingGuard.setUser(guardDetails.getUser());
        }
        if (guardDetails.getShift_start() != null) {
            existingGuard.setShift_start(guardDetails.getShift_start());
        }
        if (guardDetails.getShift_end() != null) {
            existingGuard.setShift_end(guardDetails.getShift_end());
        }
        SecurityGuard updatedGuard = securityGuardService.saveGuard(existingGuard);
        return ResponseEntity.ok(updatedGuard);
    }

    /**
     * Delete a security guard by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuard(@PathVariable Long id) {
        securityGuardService.deleteGuardById(id);
        return ResponseEntity.noContent().build();
    }
}
