package com.demo.employees.unit.tests.infrastructure.adapters.outbound.repositories.jpa;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.model.Gender;
import com.demo.employees.infrastructure.adapters.outbound.repositories.jpa.EmployeeDataModel;
import com.demo.employees.infrastructure.adapters.outbound.repositories.jpa.EmployeeRepositoryAdapter;
import com.demo.employees.infrastructure.adapters.outbound.repositories.jpa.SpringDataEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeRepositoryAdapterTest {

    SpringDataEmployeeRepository springRepo;
    EmployeeRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        springRepo = mock(SpringDataEmployeeRepository.class);
        adapter = new EmployeeRepositoryAdapter(springRepo);
    }

    private EmployeeDataModel sampleModel() {
        EmployeeDataModel m = new EmployeeDataModel();
        m.setId(1L);
        m.setFirstName("John");
        m.setLastName("Doe");
        m.setAge(30);
        m.setGender(Gender.M);
        m.setBirthDate(LocalDate.of(1995, 5, 20));
        m.setPosition("Dev");
        m.setActive(true);
        return m;
    }

    private Employee sampleDomain() {
        return new Employee(
                1L, "John", null, "Doe", null,
                30, Gender.M, LocalDate.of(1995, 5, 20), "Dev", true
        );
    }

    @Test
    void findAll_returnsMappedList() {
        when(springRepo.findAll()).thenReturn(List.of(sampleModel()));

        List<Employee> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void findById_returnsMappedEmployee() {
        when(springRepo.findById(1L)).thenReturn(Optional.of(sampleModel()));

        Optional<Employee> result = adapter.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void findByNameLike_mapsResults() {
        when(springRepo.searchByName("Jo")).thenReturn(List.of(sampleModel()));

        List<Employee> result = adapter.findByNameLike("Jo");

        assertThat(result).extracting(Employee::getFirstName).containsExactly("John");
    }

    @Test
    void save_mapsDomainToModel_andBack() {
        Employee domain = sampleDomain();
        EmployeeDataModel model = sampleModel();

        when(springRepo.save(any(EmployeeDataModel.class))).thenReturn(model);

        Employee result = adapter.save(domain);

        assertThat(result.getFirstName()).isEqualTo("John");

        ArgumentCaptor<EmployeeDataModel> captor = ArgumentCaptor.forClass(EmployeeDataModel.class);
        verify(springRepo).save(captor.capture());
        assertThat(captor.getValue().getLastName()).isEqualTo("Doe");
    }

    @Test
    void saveAll_mapsListCorrectly() {
        List<Employee> domains = List.of(sampleDomain());
        List<EmployeeDataModel> models = List.of(sampleModel());

        when(springRepo.saveAll(anyList())).thenReturn(models);

        List<Employee> result = adapter.saveAll(domains);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAge()).isEqualTo(30);
    }

    @Test
    void deleteById_delegatesToRepo() {
        adapter.deleteById(99L);

        verify(springRepo).deleteById(99L);
    }

    @Test
    void existsById_delegatesAndReturnsTrue() {
        when(springRepo.existsById(1L)).thenReturn(true);

        boolean result = adapter.existsById(1L);

        assertThat(result).isTrue();
    }
}