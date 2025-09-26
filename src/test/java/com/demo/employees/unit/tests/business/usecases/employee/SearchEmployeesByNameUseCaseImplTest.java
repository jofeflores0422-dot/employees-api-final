package com.demo.employees.unit.tests.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.SearchEmployeesByNameUseCaseImpl;
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
public class SearchEmployeesByNameUseCaseImplTest {

  @Mock EmployeeRepositoryPort repository;
  @InjectMocks SearchEmployeesByNameUseCaseImpl useCase;

  @Test
  void delegatesToRepository() {
    when(repository.findByNameLike("jo")).thenReturn(List.of(new Employee()));
    List<Employee> result = useCase.search("jo");
    assertThat(result).hasSize(1);
    verify(repository).findByNameLike("jo");
  }
}
