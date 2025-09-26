
package com.demo.employees.domain.usecases.employee;

import com.demo.employees.domain.entities.Employee;

import java.util.Optional;

public interface GetEmployeeByIdUseCase {
    Optional<Employee> getById(Long id);
}
