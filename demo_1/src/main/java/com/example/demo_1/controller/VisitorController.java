package com.example.demo_1.controller;

import com.example.demo_1.model.EntryPass;
import com.example.demo_1.model.Visitor;
import com.example.demo_1.service.VisitorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/visitors")
@AllArgsConstructor
public class VisitorController {
    private VisitorService visitorService;

    /**
     * Register a new visitor and create an entry pass for today.
     */
    @PostMapping("/register")
    public ResponseEntity<Visitor> registerNewVisitor(@RequestBody @jakarta.validation.Valid Visitor visitor) {
        try {
            Visitor registeredVisitor = visitorService.registerNewVisitor(visitor);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredVisitor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get all visitors sorted by name.
     */
    @GetMapping("/sorted-by-name")
    public ResponseEntity<List<Visitor>> getAllVisitorsSortedByName() {
        List<Visitor> visitors = visitorService.getAllVisitorsSortedByName();
        return ResponseEntity.ok(visitors);
    }

    /**
     * Get all visitors.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        return ResponseEntity.ok(visitors);
    }

    /**
     * Get a specific visitor by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Long id) {
        Visitor visitor = visitorService.getVisitorById(id);
        return ResponseEntity.ok(visitor);
    }

    /**
     * Get all visitors for a specific resident.
     */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<Visitor>> getVisitorsByResident(@PathVariable Long residentId) {
        List<Visitor> visitors = visitorService.getVisitorsByResident(residentId);
        return ResponseEntity.ok(visitors);
    }

    /**
     * Search visitors by name keyword (case-insensitive partial match).
     */
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Visitor>> searchVisitorsByName(
            @RequestParam(name = "keyword") String nameKeyword) {
        try {
            List<Visitor> visitors = visitorService.searchVisitorsByName(nameKeyword);
            return ResponseEntity.ok(visitors);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Get a visitor by CNIC (national ID number).
     */
    @GetMapping("/by-cnic/{cnic}")
    public ResponseEntity<Visitor> getVisitorByCnic(@PathVariable Number cnic) {
        Visitor visitor = visitorService.getVisitorByCnic(cnic);
        return ResponseEntity.ok(visitor);
    }

    /**
     * Search visitors by reason keyword (case-insensitive partial match).
     */
    @GetMapping("/search/by-reason")
    public ResponseEntity<List<Visitor>> searchVisitorsByReason(
            @RequestParam(name = "keyword") String reasonKeyword) {
        try {
            List<Visitor> visitors = visitorService.searchVisitorsByReason(reasonKeyword);
            return ResponseEntity.ok(visitors);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Mark a visitor as exited (sets exit time).
     */
    @PutMapping("/{id}/exit")
    public ResponseEntity<Visitor> markVisitorExit(@PathVariable Long id) {
        Visitor visitor = visitorService.markVisitorExit(id);
        return ResponseEntity.ok(visitor);
    }

    /**
     * Delete a visitor by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        visitorService.deleteVisitorById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get entry pass for a visitor for today.
     */
    @GetMapping("/{name}/entry-pass/today")
    public ResponseEntity<EntryPass> getEntryPassForVisitorToday(@PathVariable String name) {
        EntryPass entryPass = visitorService.getEntryPassForVisitorToday(name);
        return ResponseEntity.ok(entryPass);
    }

    /**
     * Get all entry passes for a specific visitor.
     */
    @GetMapping("/{name}/entry-passes")
    public ResponseEntity<List<EntryPass>> getAllEntryPassesForVisitor(@PathVariable String name) {
        List<EntryPass> entryPasses = visitorService.getAllEntryPassesForVisitor(name);
        return ResponseEntity.ok(entryPasses);
    }

    /**
     * Get all entry passes for a specific resident.
     */
    @GetMapping("/resident/{residentId}/entry-passes")
    public ResponseEntity<List<EntryPass>> getAllEntryPassesForResident(@PathVariable Long residentId) {
        List<EntryPass> entryPasses = visitorService.getAllEntryPassesForResident(residentId);
        return ResponseEntity.ok(entryPasses);
    }

    /**
     * Get all entry passes created on a specific date (query parameter format: YYYY-MM-DD).
     */
    @GetMapping("/entry-passes/by-date")
    public ResponseEntity<List<EntryPass>> getEntryPassesByDate(
            @RequestParam(name = "date") String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<EntryPass> entryPasses = visitorService.getEntryPassesByDate(date);
            return ResponseEntity.ok(entryPasses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
