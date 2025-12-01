package com.example.demo_1.dto;

import com.example.demo_1.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Long invoiceId;
    private Date paymentDate;
    private BigDecimal amountPaid;
    private PaymentMethod method;
    private String transactionRef;
}
