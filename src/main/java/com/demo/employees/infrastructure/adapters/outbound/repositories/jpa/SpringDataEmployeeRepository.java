
package com.demo.employees.infrastructure.adapters.outbound.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataEmployeeRepository extends JpaRepository<EmployeeDataModel, Long> {
    @Query("select e from EmployeeDataModel e where lower(e.firstName) like lower(concat('%', ?1, '%')) "
         + "or lower(e.middleName) like lower(concat('%', ?1, '%')) "
         + "or lower(e.lastName) like lower(concat('%', ?1, '%')) "
         + "or lower(e.secondLastName) like lower(concat('%', ?1, '%'))")
    List<EmployeeDataModel> searchByName(String namePart);
}
