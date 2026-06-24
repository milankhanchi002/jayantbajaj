package com.example.bfhl.exception;

import com.example.bfhl.dto.BfhlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @org.springframework.beans.factory.annotation.Value("${bfhl.user-id}")
    private String userId;

    @org.springframework.beans.factory.annotation.Value("${bfhl.email}")
    private String email;

    @org.springframework.beans.factory.annotation.Value("${bfhl.roll-number}")
    private String rollNumber;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleAllExceptions(Exception ex) {
        BfhlResponse err = new BfhlResponse(
                false,
                userId,
                email,
                rollNumber,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "0",
                ""
        );
        return ResponseEntity.ok(err);
    }
}
