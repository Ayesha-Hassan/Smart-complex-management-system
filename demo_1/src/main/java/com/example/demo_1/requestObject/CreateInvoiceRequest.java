package com.example.demo_1.requestObject;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateInvoiceRequest {
    private Long maintenanceRequestId;
    private Long residentId;
    private BigDecimal amount;
}
