package com.example.demo_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogDto {
    private Long id;
    private Long visitorId;
    private String visitorName;
    private Long securityGuardId;
    private String securityGuardName;
    private Date entryTime;
    private Date exitTime;
    private String purpose;
}
