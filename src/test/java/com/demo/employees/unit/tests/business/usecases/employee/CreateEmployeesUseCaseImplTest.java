package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.CreateEmployeesUseCaseImpl;
import com.demo.employees.domain.entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateEmployeesUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks CreateEmployeesUseCaseImpl useCase;

  @Test
  void savesAllAndDefaultsActiveToTrueWhenNull() {
    Employee e1 = new Employee(); e1.setActive(null);
    Employee e2 = new Employee(); e2.setActive(Boolean.TRUE);

    when(repository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

    var result = useCase.create(java.util.List.of(e1, e2));

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getActive()).isTrue();
    assertThat(result.get(1).getActive()).isTrue();
    verify(repository).saveAll(anyList());
  }
}
