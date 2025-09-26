package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.GetEmployeeByIdUseCaseImpl;
import com.demo.employees.domain.entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetEmployeeByIdUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks GetEmployeeByIdUseCaseImpl useCase;

  @Test
  void returnsEmployeeWhenExists() {
    Employee e = new Employee();
    e.setId(1L);
    when(repository.findById(1L)).thenReturn(Optional.of(e));
    Optional<Employee> found = useCase.getById(1L);
    assertThat(found).isPresent();
    assertThat(found.get().getId()).isEqualTo(1L);
  }

  @Test
  void emptyWhenNotFound() {
    when(repository.findById(99L)).thenReturn(Optional.empty());
    Optional<Employee> found = useCase.getById(99L);
    assertThat(found).isEmpty();
  }
}
