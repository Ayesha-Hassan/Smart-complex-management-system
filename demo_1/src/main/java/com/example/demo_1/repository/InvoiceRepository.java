package com.example.demo_1.repository;

import com.example.demo_1.model.Invoice;
import com.example.demo_1.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByResidentId(Long residentId);
    List<Invoice> findByStatus(InvoiceStatus status);
    List<Invoice> findByIssueDateBetween(Date startDate, Date endDate);
    Optional<Invoice> findByRequestId(Long requestId);
}
