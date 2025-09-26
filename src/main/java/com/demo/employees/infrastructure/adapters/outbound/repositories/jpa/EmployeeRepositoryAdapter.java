
package com.demo.employees.infrastructure.adapters.outbound.repositories.jpa;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.domain.entities.Employee;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final SpringDataEmployeeRepository repo;

    public EmployeeRepositoryAdapter(SpringDataEmployeeRepository repo) {
        this.repo = repo;
    }

    private static Employee toDomain(EmployeeDataModel m) {
        return new Employee(
            m.getId(), m.getFirstName(), m.getMiddleName(), m.getLastName(), m.getSecondLastName(),
            m.getAge(), m.getGender(), m.getBirthDate(), m.getPosition(), m.getActive());
    }

    private static EmployeeDataModel toModel(Employee e) {
        EmployeeDataModel m = new EmployeeDataModel();
        m.setId(e.getId()); m.setFirstName(e.getFirstName()); m.setMiddleName(e.getMiddleName());
        m.setLastName(e.getLastName()); m.setSecondLastName(e.getSecondLastName());
        m.setAge(e.getAge()); m.setGender(e.getGender()); m.setBirthDate(e.getBirthDate());
        m.setPosition(e.getPosition()); m.setActive(e.getActive());
        return m;
    }

    @Override public List<Employee> findAll() {
        return repo.findAll().stream().map(EmployeeRepositoryAdapter::toDomain).collect(Collectors.toList());
    }
    @Override public Optional<Employee> findById(Long id) {
        return repo.findById(id).map(EmployeeRepositoryAdapter::toDomain);
    }
    @Override public List<Employee> findByNameLike(String namePart) {
        return repo.searchByName(namePart).stream().map(EmployeeRepositoryAdapter::toDomain).collect(Collectors.toList());
    }
    @Override public Employee save(Employee employee) {
        return toDomain(repo.save(toModel(employee)));
    }
    @Override public List<Employee> saveAll(List<Employee> employees) {
        return repo.saveAll(employees.stream().map(EmployeeRepositoryAdapter::toModel).collect(Collectors.toList()))
                .stream().map(EmployeeRepositoryAdapter::toDomain).collect(Collectors.toList());
    }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
    @Override public boolean existsById(Long id) { return repo.existsById(id); }
}
