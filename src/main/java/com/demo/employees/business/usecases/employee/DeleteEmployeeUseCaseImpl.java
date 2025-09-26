
package com.demo.employees.business.usecases.employee;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.usecases.employee.DeleteEmployeeUseCase;

public class DeleteEmployeeUseCaseImpl implements DeleteEmployeeUseCase {
    private final EmployeeRepositoryPort repository;
    public DeleteEmployeeUseCaseImpl(EmployeeRepositoryPort repository){ this.repository = repository; }
    @Override public void delete(Long id){ repository.deleteById(id); }
}
