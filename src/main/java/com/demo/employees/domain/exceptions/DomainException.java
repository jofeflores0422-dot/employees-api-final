package com.demo.employees.domain.exceptions;

import java.util.Collections;
import java.util.List;

public abstract class DomainException extends RuntimeException {
    private final ErrorCode code;
    private final List<String> errors;

    protected DomainException(ErrorCode code, String message) {
        this(code, message, null);
    }

    protected DomainException(ErrorCode code, String message, List<String> errors) {
        super(message);
        this.code = code;
        this.errors = (errors == null) ? Collections.emptyList() : errors;
    }

    public String getCode() { return code.name(); }
    public ErrorCode getCodeEnum() { return code; }
    public List<String> errors() { return errors; }
}