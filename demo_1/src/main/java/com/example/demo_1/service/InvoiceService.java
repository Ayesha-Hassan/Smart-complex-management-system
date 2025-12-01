package com.example.demo_1.service;

import com.example.demo_1.dto.InvoiceDto;
import com.example.demo_1.exception.BadRequestException;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.Invoice;
import com.example.demo_1.model.InvoiceStatus;
import com.example.demo_1.model.MaintenanceRequest;
import com.example.demo_1.model.Resident;
import com.example.demo_1.repository.InvoiceRepository;
import com.example.demo_1.repository.MaintenanceRequestRepository;
import com.example.demo_1.repository.ResidentRepository;
import com.example.demo_1.requestObject.CreateInvoiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final ResidentRepository residentRepository;

    @Transactional(readOnly = true)
    public List<InvoiceDto> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        return mapToDto(invoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDto> getInvoicesByResidentId(Long residentId) {
        return invoiceRepository.findByResidentId(residentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceDto> getInvoicesByStatus(InvoiceStatus status) {
        return invoiceRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public InvoiceDto createInvoice(CreateInvoiceRequest request) {
        // Validate resident
        Resident resident = residentRepository.findById(request.getResidentId())
                .orElseThrow(() -> new ResourceNotFoundException("Resident", "id", request.getResidentId()));

        Invoice invoice = new Invoice();
        invoice.setResident(resident);
        invoice.setAmount(request.getAmount());
        invoice.setIssueDate(new Date());
        invoice.setStatus(InvoiceStatus.UNPAID);

        // Link to maintenance request if provided
        if (request.getMaintenanceRequestId() != null) {
            MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(request.getMaintenanceRequestId())
                    .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", "id", request.getMaintenanceRequestId()));
            
            // Check if invoice already exists for this request
            invoiceRepository.findByRequestId(request.getMaintenanceRequestId())
                    .ifPresent(existingInvoice -> {
                        throw new BadRequestException("Invoice already exists for this maintenance request");
                    });
            
            invoice.setRequest(maintenanceRequest);
        }

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return mapToDto(savedInvoice);
    }

    @Transactional
    public InvoiceDto updateInvoiceStatus(Long id, InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        
        invoice.setStatus(status);
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        return mapToDto(updatedInvoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new BadRequestException("Cannot delete a paid invoice");
        }
        
        invoiceRepository.delete(invoice);
    }

    private InvoiceDto mapToDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        dto.setId(invoice.getId());
        dto.setAmount(invoice.getAmount());
        dto.setIssueDate(invoice.getIssueDate());
        dto.setStatus(invoice.getStatus());
        dto.setPaid(invoice.getStatus() == InvoiceStatus.PAID);

        if (invoice.getResident() != null) {
            dto.setResidentId(invoice.getResident().getId());
            if (invoice.getResident().getUser() != null) {
                dto.setResidentName(invoice.getResident().getUser().getName());
            }
        }

        if (invoice.getRequest() != null) {
            dto.setRequestId(invoice.getRequest().getId());
            dto.setRequestDescription(invoice.getRequest().getDescription());
        }

        return dto;
    }
}
