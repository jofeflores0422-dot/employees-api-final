package com.demo.employees.unit.tests.infrastructure.interfaces.rest;

import com.demo.employees.domain.exceptions.NotFoundException;
import com.demo.employees.infrastructure.adapters.inbound.dtos.ErrorResponseDTO;
import com.demo.employees.infrastructure.interfaces.rest.ExceptionsHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static com.demo.employees.domain.exceptions.ErrorCode.EMPLOYEE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ExceptionsHandlerTest {

  ExceptionsHandler handler = new ExceptionsHandler();

  @AfterEach
  void clearMdc() { MDC.clear(); }

  @Test
  void notFound_returns404_andCopiesTraceId() {
    MDC.put("traceId", "trace-1");
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    when(req.getRequestURI()).thenReturn("/employees/99");

    NotFoundException ex = Mockito.mock(NotFoundException.class);
    when(ex.getCode()).thenReturn(String.valueOf(EMPLOYEE_NOT_FOUND));
    when(ex.getMessage()).thenReturn("Employee with id 99 not found");
    when(ex.errors()).thenReturn(List.of("Employee with id 99 not found"));

    ResponseEntity<ErrorResponseDTO> resp = handler.handleNotFound(ex, req);

    assertThat(resp.getStatusCode().value()).isEqualTo(404);
    assertThat(resp.getBody().getCode()).isEqualTo("EMPLOYEE_NOT_FOUND");
    assertThat(resp.getBody().getErrors()).containsExactly("Employee with id 99 not found");
    assertThat(resp.getBody().getTraceId()).isEqualTo("trace-1");
  }

  @Test
  void badRequest_withValidationErrors_returns400() {
    MDC.put("traceId", "trace-2");
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    when(req.getRequestURI()).thenReturn("/employees");

    BeanPropertyBindingResult binding = new BeanPropertyBindingResult(new Object(), "emp");
    binding.addError(new FieldError("emp", "firstName", "must not be blank"));
    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, binding);

    ResponseEntity<ErrorResponseDTO> resp = handler.handleBadRequest(ex, req);

    assertThat(resp.getStatusCode().value()).isEqualTo(400);
    assertThat(resp.getBody().getCode()).isEqualTo("VALIDATION_ERROR");
    assertThat(resp.getBody().getErrors()).containsExactly("firstName: must not be blank");
    assertThat(resp.getBody().getTraceId()).isEqualTo("trace-2");
  }

  @Test
  void internalError_returns500() {
    MDC.put("traceId", "trace-5");
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    when(req.getRequestURI()).thenReturn("/employees");

    RuntimeException ex = new RuntimeException("boom");
    ResponseEntity<ErrorResponseDTO> resp = handler.handleInternal(ex, req);

    assertThat(resp.getStatusCode().value()).isEqualTo(500);
    assertThat(resp.getBody().getCode()).isEqualTo("INTERNAL_ERROR");
    assertThat(resp.getBody().getErrors()).containsExactly("RuntimeException");
    assertThat(resp.getBody().getTraceId()).isEqualTo("trace-5");
  }
}