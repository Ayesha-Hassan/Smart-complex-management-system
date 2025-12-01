package com.example.demo_1.repository;

import com.example.demo_1.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    /**
     * Find all visitors ordered by name (ascending).
     * Uses named query: Visitor.findAllOrderedByName
     */
    List<Visitor> findAllByOrderByNameAsc();

    /**
     * Find all visitors for a specific resident.
     * Uses named query: Visitor.findByResidentId
     */
    List<Visitor> findByResidentId(Long residentId);

    /**
     * Search visitors by name keyword (case-insensitive partial match).
     * Uses named query: Visitor.findByNameContaining
     */
    List<Visitor> findByNameContaining(String nameKeyword);

    /**
     * Find a visitor by CNIC (national ID number).
     * Uses named query: Visitor.findByCnic
     */
    Optional<Visitor> findByCnic(Number cnic);

    /**
     * Find visitors by reason keyword (case-insensitive partial match).
     * Uses named query: Visitor.findByReason
     */
    List<Visitor> findByReason(String reasonKeyword);
}
