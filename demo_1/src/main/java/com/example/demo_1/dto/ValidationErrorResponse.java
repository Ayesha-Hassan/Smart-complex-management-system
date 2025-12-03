package com.example.demo_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<FieldError> errors;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String message;
    }
}
