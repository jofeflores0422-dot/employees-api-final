
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.usecases.employee.CreateEmployeesUseCase;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CreateEmployeesUseCaseImpl implements CreateEmployeesUseCase {
    private final EmployeeRepositoryPort repository;
    public CreateEmployeesUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }

    @Override
    public List<Employee> create(List<Employee> employees) {
        OffsetDateTime now = OffsetDateTime.now();
        List<Employee> prepared = employees.stream().map(e -> {
            if (e.getActive() == null) e.setActive(Boolean.TRUE);
            return e;
        }).collect(Collectors.toList());
        return repository.saveAll(prepared);
    }
}
