package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.UpdateEmployeeUseCaseImpl;
import com.demo.employees.domain.entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateEmployeeUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks UpdateEmployeeUseCaseImpl useCase;

  @Test
  void updatesByDelegatingToRepositorySave() {
    Employee current = new Employee(); current.setId(1L);
    when(repository.save(any(Employee.class))).thenAnswer(inv -> inv.getArgument(0));
    Employee saved = useCase.update(1L, current);
    assertThat(saved.getId()).isEqualTo(1L);
    verify(repository).save(any(Employee.class));
  }
}
