package com.demo.employees.unit.tests.infrastructure.adapters.inbound.handlers;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.exceptions.NotFoundException;
import com.demo.employees.domain.model.Gender;
import com.demo.employees.domain.usecases.employee.CreateEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.DeleteEmployeeUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeeByIdUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.SearchEmployeesByNameUseCase;
import com.demo.employees.domain.usecases.employee.UpdateEmployeeUseCase;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import com.demo.employees.infrastructure.adapters.inbound.handlers.employee.EmployeeCommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeCommandHandlerTest {

    CreateEmployeesUseCase createUC;
    GetEmployeesUseCase getAllUC;
    GetEmployeeByIdUseCase getByIdUC;
    UpdateEmployeeUseCase updateUC;
    DeleteEmployeeUseCase deleteUC;
    SearchEmployeesByNameUseCase searchUC;
    EmployeeCommandHandler handler;

    @BeforeEach
    void setUp() {
        createUC = mock(CreateEmployeesUseCase.class);
        getAllUC = mock(GetEmployeesUseCase.class);
        getByIdUC = mock(GetEmployeeByIdUseCase.class);
        updateUC = mock(UpdateEmployeeUseCase.class);
        deleteUC = mock(DeleteEmployeeUseCase.class);
        searchUC = mock(SearchEmployeesByNameUseCase.class);
        handler = new EmployeeCommandHandler(createUC, getAllUC, getByIdUC, updateUC, deleteUC, searchUC);
    }

    private Employee sampleDomain() {
        return new Employee(1L, "John", null, "Doe", null, 30, Gender.M, LocalDate.of(1995, 5, 20), "Dev", true);
    }

    @Test
    void create_returnsMappedResponses() {
        Employee domain = sampleDomain();
        when(createUC.create(anyList())).thenReturn(List.of(domain));

        EmployeeRequestDTO req = new EmployeeRequestDTO();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setAge(30);
        req.setGender(Gender.M);
        req.setBirthDate(LocalDate.of(1995, 5, 20));
        req.setPosition("Dev");
        req.setActive(true);

        List<EmployeeResponseDTO> result = handler.create(List.of(req));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void getAll_returnsListOfEmployees() {
        when(getAllUC.getAll()).thenReturn(List.of(sampleDomain()));
        List<EmployeeResponseDTO> result = handler.getAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    void getById_returnsEmployeeResponse() {
        when(getByIdUC.getById(1L)).thenReturn(Optional.of(sampleDomain()));
        EmployeeResponseDTO result = handler.getById(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
    }

    @Test
    void getById_notFound_throwsNotFoundException() {
        when(getByIdUC.getById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> handler.getById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Employee 99 not found")
                .hasFieldOrPropertyWithValue("code", "EMPLOYEE_NOT_FOUND");
    }

    @Test
    void update_returnsUpdatedResponse() {
        Employee updated = new Employee(
                1L, "Ana", null, "Doe", null,
                25, Gender.F, LocalDate.of(2000, 1, 15), "QA", true
        );
        when(updateUC.update(eq(1L), any(Employee.class))).thenReturn(updated);

        EmployeeRequestDTO req = new EmployeeRequestDTO();
        req.setFirstName("Ana");
        req.setLastName("Doe");
        req.setAge(25);
        req.setGender(Gender.F);
        req.setBirthDate(LocalDate.of(2000, 1, 15));
        req.setPosition("QA");
        req.setActive(true);

        EmployeeResponseDTO result = handler.update(1L, req);

        assertThat(result.getFirstName()).isEqualTo("Ana");
        assertThat(result.getAge()).isEqualTo(25);
        assertThat(result.getPosition()).isEqualTo("QA");
    }

    @Test
    void updatePartial_updatesFieldsAndReturnsResponse() {
        Employee current = sampleDomain();
        when(getByIdUC.getById(1L)).thenReturn(Optional.of(current));
        when(updateUC.update(eq(1L), any(Employee.class))).thenAnswer(inv -> inv.getArgument(1));
        EmployeePatchDTO patch = new EmployeePatchDTO();
        patch.setFirstName("Carlos");
        EmployeeResponseDTO result = handler.updatePartial(1L, patch);
        assertThat(result.getFirstName()).isEqualTo("Carlos");
    }

    @Test
    void updatePartial_notFound_throwsNotFound() {
        when(getByIdUC.getById(404L)).thenReturn(Optional.empty());
        EmployeePatchDTO patch = new EmployeePatchDTO();
        assertThatThrownBy(() -> handler.updatePartial(404L, patch))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Employee 404 not found")
                .hasFieldOrPropertyWithValue("code", "EMPLOYEE_NOT_FOUND");
    }

    @Test
    void delete_invokesUseCase() {
        handler.delete(77L);
        verify(deleteUC).delete(77L);
    }

    @Test
    void search_returnsMappedList() {
        when(searchUC.search("Jo")).thenReturn(List.of(sampleDomain()));
        List<EmployeeResponseDTO> result = handler.search("Jo");
        assertThat(result).extracting(EmployeeResponseDTO::getFirstName).containsExactly("John");
    }
}