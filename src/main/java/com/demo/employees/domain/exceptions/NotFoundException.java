package com.demo.employees.domain.exceptions;


import java.util.List;

public class NotFoundException extends DomainException {
    public NotFoundException(ErrorCode code, String message) {
        super(code, message);
    }
    public NotFoundException(ErrorCode code, String message, List<String> errors) {
        super(code, message, errors);
    }
}