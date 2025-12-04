package com.example.demo_1.service;

import com.example.demo_1.model.EntryPass;
import com.example.demo_1.model.Visitor;
import com.example.demo_1.repository.EntryPassRepository;
import com.example.demo_1.repository.VisitorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class VisitorService {
    private VisitorRepository visitorRepository;
    private EntryPassRepository entryPassRepository;

    /**
     * Register a new visitor - requires an existing entry pass for today.
     * The entry pass must be created in the system beforehand to allow entry.
     */
    public Visitor registerNewVisitor(Visitor visitor) {
        if (visitor.getName() == null || visitor.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Visitor name is required");
        }
        if (visitor.getResident() == null) {
            throw new IllegalArgumentException("Visiting resident is required");
        }

        // Verify that an entry pass exists for this visitor today
        entryPassRepository.findByVisitorNameAndCreatedDate(visitor.getName(), LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No valid entry pass found for visitor today. Entry pass must be created in the system first."));

        // Set entry time to current time if not provided
        if (visitor.getEntry_time() == null) {
            visitor.setEntry_time(Time.valueOf(LocalTime.now()));
        }

        // Set entry date to today
        visitor.setEntry_date(LocalDate.now());

        // Save the visitor
        return visitorRepository.save(visitor);
    }

    /**
     * Retrieve all visitors sorted by name.
     */
    public List<Visitor> getAllVisitorsSortedByName() {
        return visitorRepository.findAllByOrderByNameAsc();
    }

    /**
     * Retrieve all visitors for a specific resident.
     */
    public List<Visitor> getVisitorsByResident(Long residentId) {
        return visitorRepository.findByResidentId(residentId);
    }

    /**
     * Search visitors by name keyword (case-insensitive partial match).
     */
    public List<Visitor> searchVisitorsByName(String nameKeyword) {
        if (nameKeyword == null || nameKeyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Name keyword cannot be empty");
        }
        return visitorRepository.findByNameContaining(nameKeyword);
    }

    /**
     * Find a visitor by CNIC (national ID number).
     */
    public Visitor getVisitorByCnic(String cnic) {
        return visitorRepository.findByCnic(cnic)
                .orElseThrow(() -> new RuntimeException("Visitor not found with CNIC: " + cnic));
    }

    /**
     * Search visitors by reason keyword (case-insensitive partial match).
     */
    public List<Visitor> searchVisitorsByReason(String reasonKeyword) {
        if (reasonKeyword == null || reasonKeyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason keyword cannot be empty");
        }
        return visitorRepository.findByReason(reasonKeyword);
    }

    /**
     * Retrieve a specific visitor by ID.
     */
    public Visitor getVisitorById(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found with ID: " + id));
    }

    /**
     * Retrieve all visitors.
     */
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    /**
     * Update visitor exit time (marking visitor as left).
     */
    public Visitor markVisitorExit(Long visitorId) {
        Visitor visitor = getVisitorById(visitorId);
        visitor.setExit_time(Time.valueOf(LocalTime.now()));
        return visitorRepository.save(visitor);
    }

    /**
     * Delete a visitor by ID.
     */
    public void deleteVisitorById(Long id) {
        if (!visitorRepository.existsById(id)) {
            throw new RuntimeException("Visitor not found with ID: " + id);
        }
        visitorRepository.deleteById(id);
    }

    /**
     * Retrieve entry pass for a visitor on a specific date.
     */
    public EntryPass getEntryPassForVisitorToday(String name) {
        return entryPassRepository.findByVisitorNameAndCreatedDate( name, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No entry pass found for visitor today"));
    }

    /**
     * Retrieve all entry passes for a visitor.
     */
    public List<EntryPass> getAllEntryPassesForVisitor(String name) {
        return entryPassRepository.findByVisitorName(name);
    }

    /**
     * Retrieve all entry passes for a resident.
     */
    public List<EntryPass> getAllEntryPassesForResident(Long residentId) {
        return entryPassRepository.findByResidentId(residentId);
    }

    /**
     * Retrieve all entry passes created on a specific date.
     */
    public List<EntryPass> getEntryPassesByDate(LocalDate date) {
        return entryPassRepository.findByCreatedDate(date);
    }
}
