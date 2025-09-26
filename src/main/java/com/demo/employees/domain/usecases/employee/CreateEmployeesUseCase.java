
package com.demo.employees.domain.usecases.employee;

import com.demo.employees.domain.entities.Employee;

import java.util.List;

public interface CreateEmployeesUseCase {
    List<Employee> create(List<Employee> employees);
}
