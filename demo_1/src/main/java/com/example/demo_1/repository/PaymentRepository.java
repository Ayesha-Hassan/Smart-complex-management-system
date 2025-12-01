package com.example.demo_1.repository;

import com.example.demo_1.model.Payment;
import com.example.demo_1.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByInvoiceId(Long invoiceId);
    List<Payment> findByPaymentDateBetween(Date startDate, Date endDate);
    List<Payment> findByMethod(PaymentMethod method);
    
    @Query("SELECT SUM(p.amountPaid) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalRevenue(Date startDate, Date endDate);
}
