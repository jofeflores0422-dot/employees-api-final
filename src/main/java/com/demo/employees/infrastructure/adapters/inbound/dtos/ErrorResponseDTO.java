package com.demo.employees.infrastructure.adapters.inbound.dtos;

import java.time.OffsetDateTime;
import java.util.List;

public class ErrorResponseDTO {
    private final String timestamp;
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final String path;
    private final String traceId;
    private final List<String> errors;

    private ErrorResponseDTO(int status,
                             String error,
                             String code,
                             String message,
                             String path,
                             String traceId,
                             List<String> errors) {
        this.timestamp = OffsetDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.code = code;
        this.message = message;
        this.path = path;
        this.traceId = traceId;
        this.errors = errors;
    }

    public static ErrorResponseDTO of(int status,
                                      String error,
                                      String code,
                                      String message,
                                      String path,
                                      String traceId,
                                      List<String> errors) {
        return new ErrorResponseDTO(status, error, code, message, path, traceId, errors);
    }

    public String getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public String getTraceId() { return traceId; }
    public List<String> getErrors() { return errors; }
}