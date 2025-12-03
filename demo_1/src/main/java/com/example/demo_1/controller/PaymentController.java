package com.example.demo_1.controller;

import com.example.demo_1.dto.PaymentDto;
import com.example.demo_1.requestObject.ProcessPaymentRequest;
import com.example.demo_1.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        PaymentDto payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/invoice/{invoiceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<PaymentDto> getPaymentByInvoice(@PathVariable Long invoiceId) {
        PaymentDto payment = paymentService.getPaymentByInvoiceId(invoiceId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RESIDENT')")
    public ResponseEntity<PaymentDto> processPayment(@RequestBody @jakarta.validation.Valid ProcessPaymentRequest request) {
        PaymentDto payment = paymentService.processPayment(request);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getRevenue(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        BigDecimal revenue = paymentService.calculateRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }
}
