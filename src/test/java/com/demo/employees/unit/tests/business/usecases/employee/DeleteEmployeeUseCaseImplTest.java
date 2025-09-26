package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.DeleteEmployeeUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteEmployeeUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks DeleteEmployeeUseCaseImpl useCase;

  @Test
  void deletesById() {
    useCase.delete(5L);
    verify(repository).deleteById(5L);
  }
}
