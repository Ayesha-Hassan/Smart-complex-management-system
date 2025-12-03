package com.example.demo_1.requestObject;

import com.example.demo_1.model.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProcessPaymentRequest {
    @NotNull(message = "Invoice ID is required")
    private Long invoiceId;
    
    @NotNull(message = "Amount paid is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amountPaid;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod method;
    
    @NotBlank(message = "Transaction reference is required")
    private String transactionRef;
}
