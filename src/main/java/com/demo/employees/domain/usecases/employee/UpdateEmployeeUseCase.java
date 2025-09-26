
package com.demo.employees.domain.usecases.employee;

import com.demo.employees.domain.entities.Employee;

public interface UpdateEmployeeUseCase {
    Employee update(Long id, Employee employee);
}
