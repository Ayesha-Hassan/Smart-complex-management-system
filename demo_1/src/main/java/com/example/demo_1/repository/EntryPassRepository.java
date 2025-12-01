package com.example.demo_1.repository;

import com.example.demo_1.model.EntryPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EntryPassRepository extends JpaRepository<EntryPass, Long> {

    /**
     * Find all entry passes for a specific visitor.
     * Uses named query: EntryPass.findByVisitorId
     */
    List<EntryPass> findByVisitorName(String name);

    /**
     * Find all entry passes for a specific resident.
     * Uses named query: EntryPass.findByResidentId
     */
    List<EntryPass> findByResidentId(Long residentId);

    /**
     * Find all entry passes created on a specific date.
     * Uses named query: EntryPass.findByCreatedDate
     */
    List<EntryPass> findByCreatedDate(LocalDate createdDate);

    /**
     * Find entry pass for a specific visitor on a specific date.
     * Uses named query: EntryPass.findByVisitorAndCreatedDate
     */
    Optional<EntryPass> findByVisitorNameAndCreatedDate(String name, LocalDate createdDate);
}

