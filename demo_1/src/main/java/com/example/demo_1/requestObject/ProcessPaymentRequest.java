package com.example.demo_1.requestObject;

import com.example.demo_1.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProcessPaymentRequest {
    private Long invoiceId;
    private BigDecimal amountPaid;
    private PaymentMethod method;
    private String transactionRef;
}
