
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.usecases.employee.GetEmployeeByIdUseCase;

import java.util.Optional;

public class GetEmployeeByIdUseCaseImpl implements GetEmployeeByIdUseCase {
    private final EmployeeRepositoryPort repository;
    public GetEmployeeByIdUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }
    @Override public Optional<Employee> getById(Long id){ return repository.findById(id); }
}
