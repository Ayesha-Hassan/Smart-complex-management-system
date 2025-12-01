package com.example.demo_1.service;

import com.example.demo_1.dto.PaymentDto;
import com.example.demo_1.exception.BadRequestException;
import com.example.demo_1.exception.ResourceNotFoundException;
import com.example.demo_1.model.Invoice;
import com.example.demo_1.model.InvoiceStatus;
import com.example.demo_1.model.Payment;
import com.example.demo_1.repository.InvoiceRepository;
import com.example.demo_1.repository.PaymentRepository;
import com.example.demo_1.requestObject.ProcessPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentService {

    private  PaymentRepository paymentRepository;
    private  InvoiceRepository invoiceRepository;

    @Transactional(readOnly = true)
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return mapToDto(payment);
    }

    @Transactional(readOnly = true)
    public PaymentDto getPaymentByInvoiceId(Long invoiceId) {
        Payment payment = paymentRepository.findByInvoiceId(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment for invoice", "invoiceId", invoiceId));
        return mapToDto(payment);
    }

    @Transactional
    public PaymentDto processPayment(ProcessPaymentRequest request) {
        // Validate invoice exists
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", request.getInvoiceId()));

        // Check if invoice is already paid
        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new BadRequestException("Invoice is already paid");
        }

        // Validate payment amount
        if (request.getAmountPaid().compareTo(invoice.getAmount()) != 0) {
            throw new BadRequestException("Payment amount must match invoice amount");
        }

        // Create payment record
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPaymentDate(new Date());
        payment.setAmountPaid(request.getAmountPaid());
        payment.setMethod(request.getMethod());
        payment.setTransactionRef(request.getTransactionRef());

        Payment savedPayment = paymentRepository.save(payment);

        // Update invoice status to PAID
        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.save(invoice);

        return mapToDto(savedPayment);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateRevenue(Date startDate, Date endDate) {
        BigDecimal revenue = paymentRepository.calculateTotalRevenue(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    private PaymentDto mapToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setMethod(payment.getMethod());
        dto.setTransactionRef(payment.getTransactionRef());

        if (payment.getInvoice() != null) {
            dto.setInvoiceId(payment.getInvoice().getId());
        }

        return dto;
    }
}
