package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.GetEmployeesUseCaseImpl;
import com.demo.employees.domain.entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetEmployeesUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks GetEmployeesUseCaseImpl useCase;

  @Test
  void returnsAllEmployees() {
    when(repository.findAll()).thenReturn(List.of(new Employee(), new Employee()));
    List<Employee> result = useCase.getAll();
    assertThat(result).hasSize(2);
    verify(repository).findAll();
  }
}
