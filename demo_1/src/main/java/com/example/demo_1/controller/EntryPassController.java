package com.example.demo_1.controller;

import com.example.demo_1.model.EntryPass;
import com.example.demo_1.model.Resident;
import com.example.demo_1.model.Visitor;
import com.example.demo_1.service.EntryPassService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/entry-passes")
public class EntryPassController {
    private EntryPassService entryPassService;

    /**
     * Create a new entry pass for a visitor to a resident.
     * Entry pass must exist before a visitor can be registered.
     * Request body should contain: visitorId, residentId
     */
    @PostMapping("/create")
    public ResponseEntity<EntryPass> createEntryPass(@RequestBody Map<String, Object> request) {
        try {
            // Extract visitor and resident IDs from request
            Long visitorId = ((Number) request.get("visitorId")).longValue();
            Long residentId = ((Number) request.get("residentId")).longValue();

            // Create minimal objects with IDs for the relationship
            Visitor visitor = new Visitor();
            visitor.setId(visitorId);

            Resident resident = new Resident();
            resident.setId(residentId);

            EntryPass entryPass = entryPassService.createEntryPass(visitor, resident);
            return ResponseEntity.status(HttpStatus.CREATED).body(entryPass);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all entry passes.
     */
    @GetMapping("/all")
    public ResponseEntity<List<EntryPass>> getAllEntryPasses() {
        List<EntryPass> entryPasses = entryPassService.getAllEntryPasses();
        return ResponseEntity.ok(entryPasses);
    }

    /**
     * Get a specific entry pass by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntryPass> getEntryPassById(@PathVariable Long id) {
        EntryPass entryPass = entryPassService.getEntryPassById(id);
        return ResponseEntity.ok(entryPass);
    }

    /**
     * Get all entry passes for a specific visitor.
     */
    @GetMapping("/visitor/{name}")
    public ResponseEntity<List<EntryPass>> getEntryPassesByVisitor(@PathVariable String name) {
        List<EntryPass> entryPasses = entryPassService.getEntryPassesByVisitor(name);
        return ResponseEntity.ok(entryPasses);
    }

    /**
     * Get all entry passes for a specific resident.
     */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<EntryPass>> getEntryPassesByResident(@PathVariable Long residentId) {
        List<EntryPass> entryPasses = entryPassService.getEntryPassesByResident(residentId);
        return ResponseEntity.ok(entryPasses);
    }

    /**
     * Get all entry passes created on a specific date (format: YYYY-MM-DD).
     */
    @GetMapping("/by-date")
    public ResponseEntity<List<EntryPass>> getEntryPassesByCreatedDate(
            @RequestParam(name = "date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<EntryPass> entryPasses = entryPassService.getEntryPassesByCreatedDate(date);
            return ResponseEntity.ok(entryPasses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get entry pass for a specific visitor on a specific date.
     * Format: YYYY-MM-DD
     */
    @GetMapping("/visitor/{visitorId}/date")
    public ResponseEntity<EntryPass> getEntryPassForVisitorOnDate(
            @PathVariable String name,
            @RequestParam(name = "date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            EntryPass entryPass = entryPassService.getEntryPassForVisitorOnDate(name, date);
            return ResponseEntity.ok(entryPass);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Delete an entry pass by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntryPass(@PathVariable Long id) {
        entryPassService.deleteEntryPassById(id);
        return ResponseEntity.noContent().build();
    }
}
