package com.example.demo_1.dto;

import com.example.demo_1.model.InvoiceStatus;
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
public class InvoiceDto {
    private Long id;
    private Long requestId;
    private String requestDescription;
    private Long residentId;
    private String residentName;
    private BigDecimal amount;
    private Date issueDate;
    private InvoiceStatus status;
    private boolean isPaid;
}
