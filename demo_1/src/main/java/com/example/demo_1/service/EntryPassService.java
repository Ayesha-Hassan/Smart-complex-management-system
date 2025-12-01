package com.example.demo_1.service;

import com.example.demo_1.model.EntryPass;
import com.example.demo_1.model.Resident;
import com.example.demo_1.model.Visitor;
import com.example.demo_1.repository.EntryPassRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryPassService {
    private EntryPassRepository entryPassRepository;

    /**
     * Create a new entry pass for a visitor to a resident.
     * This must be done before the visitor can be registered/enter the complex.
     */
    public EntryPass createEntryPass(Visitor visitor, Resident resident) {
        if (visitor == null) {
            throw new IllegalArgumentException("Visitor is required");
        }
        if (resident == null) {
            throw new IllegalArgumentException("Resident is required");
        }

        EntryPass entryPass = new EntryPass();
        entryPass.setVisitor(visitor);
        entryPass.setResident(resident);
        entryPass.setCreated_at(Time.valueOf(LocalTime.now()));
        entryPass.setCreatedDate(LocalDate.now());

        return entryPassRepository.save(entryPass);
    }

    /**
     * Retrieve a specific entry pass by ID.
     */
    public EntryPass getEntryPassById(Long id) {
        return entryPassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry pass not found with ID: " + id));
    }

    /**
     * Retrieve all entry passes for a specific visitor.
     */
    public List<EntryPass> getEntryPassesByVisitor(String name) {
        return entryPassRepository.findByVisitorName(name);
    }

    /**
     * Retrieve all entry passes for a specific resident.
     */
    public List<EntryPass> getEntryPassesByResident(Long residentId) {
        return entryPassRepository.findByResidentId(residentId);
    }

    /**
     * Retrieve all entry passes created on a specific date.
     */
    public List<EntryPass> getEntryPassesByCreatedDate(LocalDate date) {
        return entryPassRepository.findByCreatedDate(date);
    }

    /**
     * Retrieve entry pass for a visitor on a specific date.
     */
    public EntryPass getEntryPassForVisitorOnDate(String name, LocalDate date) {
        return entryPassRepository.findByVisitorNameAndCreatedDate(name, date)
                .orElseThrow(() -> new RuntimeException("No entry pass found for visitor on " + date));
    }

    /**
     * Delete an entry pass by ID.
     */
    public void deleteEntryPassById(Long id) {
        if (!entryPassRepository.existsById(id)) {
            throw new RuntimeException("Entry pass not found with ID: " + id);
        }
        entryPassRepository.deleteById(id);
    }

    /**
     * Retrieve all entry passes.
     */
    public List<EntryPass> getAllEntryPasses() {
        return entryPassRepository.findAll();
    }
}
