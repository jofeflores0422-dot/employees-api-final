
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.usecases.employee.SearchEmployeesByNameUseCase;

import java.util.List;

public class SearchEmployeesByNameUseCaseImpl implements SearchEmployeesByNameUseCase {
    private final EmployeeRepositoryPort repository;
    public SearchEmployeesByNameUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }
    @Override public List<Employee> search(String namePart){ return repository.findByNameLike(namePart); }
}
