
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.usecases.employee.GetEmployeesUseCase;

import java.util.List;

public class GetEmployeesUseCaseImpl implements GetEmployeesUseCase {
    private final EmployeeRepositoryPort repository;
    public GetEmployeesUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }
    @Override public List<Employee> getAll(){ return repository.findAll(); }
}
