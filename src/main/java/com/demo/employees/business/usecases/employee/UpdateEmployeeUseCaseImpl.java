
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.usecases.employee.UpdateEmployeeUseCase;

public class UpdateEmployeeUseCaseImpl implements UpdateEmployeeUseCase {
    private final EmployeeRepositoryPort repository;
    public UpdateEmployeeUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }
    @Override public Employee update(Long id, Employee employee){
        employee.setId(id);
        return repository.save(employee);
    }
}
