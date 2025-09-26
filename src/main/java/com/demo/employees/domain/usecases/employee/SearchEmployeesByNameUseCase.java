
package com.demo.employees.domain.usecases.employee;

import com.demo.employees.domain.entities.Employee;

import java.util.List;

public interface SearchEmployeesByNameUseCase {
    List<Employee> search(String namePart);
}
