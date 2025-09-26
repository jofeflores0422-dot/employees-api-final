
package com.demo.employees.business.ports;

import com.demo.employees.domain.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    List<Employee> findByNameLike(String namePart);
    Employee save(Employee employee);
    List<Employee> saveAll(List<Employee> employees);
    void deleteById(Long id);
    boolean existsById(Long id);
}
