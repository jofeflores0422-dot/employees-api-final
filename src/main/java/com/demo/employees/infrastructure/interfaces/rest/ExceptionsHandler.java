package com.demo.employees.infrastructure.interfaces.rest;

import com.demo.employees.domain.exceptions.NotFoundException;
import com.demo.employees.infrastructure.adapters.inbound.dtos.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NotFoundException e, HttpServletRequest req) {
        String traceId = MDC.get("traceId");
        ErrorResponseDTO body = ErrorResponseDTO.of(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getCode(),
                e.getMessage(),
                req.getRequestURI(),
                traceId,
                e.errors()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception ex, HttpServletRequest req) {
        String traceId = MDC.get("traceId");
        List<String> details = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException manve) {
            for (FieldError fe : manve.getBindingResult().getFieldErrors()) {
                details.add(fe.getField() + ": " + fe.getDefaultMessage());
            }
        }

        ErrorResponseDTO body = ErrorResponseDTO.of(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "VALIDATION_ERROR",
                "Request validation failed",
                req.getRequestURI(),
                traceId,
                details
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleInternal(Exception ex, HttpServletRequest req) {
        String traceId = MDC.get("traceId");

        ErrorResponseDTO body = ErrorResponseDTO.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                req.getRequestURI(),
                traceId,
                List.of(ex.getClass().getSimpleName())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}