package com.example.demo_1.controller;

import com.example.demo_1.dto.InvoiceDto;
import com.example.demo_1.model.InvoiceStatus;
import com.example.demo_1.requestObject.CreateInvoiceRequest;
import com.example.demo_1.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        List<InvoiceDto> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable Long id) {
        InvoiceDto invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByResident(@PathVariable Long residentId) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByResidentId(residentId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InvoiceDto>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
        List<InvoiceDto> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody @jakarta.validation.Valid CreateInvoiceRequest request) {
        InvoiceDto createdInvoice = invoiceService.createInvoice(request);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<InvoiceDto> updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status) {
        InvoiceDto updatedInvoice = invoiceService.updateInvoiceStatus(id, status);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
