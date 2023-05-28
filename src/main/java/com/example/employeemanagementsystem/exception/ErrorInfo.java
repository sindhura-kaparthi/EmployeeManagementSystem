package com.example.employeemanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ErrorInfo {
    private Integer errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
}
