package com.example.demo_1.dto;

import com.example.demo_1.model.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessCardDto {
    private Long id;
    private String cardNumber;
    private Long residentId;
    private String residentName;
    private Date issueDate;
    private Date expiryDate;
    private CardStatus status;
}
