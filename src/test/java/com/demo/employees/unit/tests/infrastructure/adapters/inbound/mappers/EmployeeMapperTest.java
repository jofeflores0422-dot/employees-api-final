package com.demo.employees.unit.tests.infrastructure.adapters.inbound.mappers;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.model.Gender;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import com.demo.employees.infrastructure.adapters.inbound.mappers.EmployeeMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeMapperTest {

  @Test
  void toDomain_mapsAllFields() {
    EmployeeRequestDTO req = new EmployeeRequestDTO();
    req.setFirstName("John");
    req.setMiddleName("A");
    req.setLastName("Doe");
    req.setSecondLastName("Roe");
    req.setAge(28);
    req.setGender(Gender.F);
    req.setBirthDate(LocalDate.of(1997, 8, 5));
    req.setPosition("Engineer");
    req.setActive(true);

    Employee e = EmployeeMapper.toDomain(req);

    assertThat(e.getFirstName()).isEqualTo("John");
    assertThat(e.getMiddleName()).isEqualTo("A");
    assertThat(e.getLastName()).isEqualTo("Doe");
    assertThat(e.getSecondLastName()).isEqualTo("Roe");
    assertThat(e.getAge()).isEqualTo(28);
    assertThat(e.getGender()).isEqualTo(Gender.F);
    assertThat(e.getBirthDate()).isEqualTo(LocalDate.of(1997, 8, 5));
    assertThat(e.getPosition()).isEqualTo("Engineer");
    assertThat(e.getActive()).isTrue();
  }

  @Test
  void toResponse_mapsAllFields() {
    Employee e = new Employee();
    e.setId(10L);
    e.setFirstName("Jane");
    e.setMiddleName("B");
    e.setLastName("Doe");
    e.setSecondLastName("Roe");
    e.setAge(30);
    e.setGender(Gender.F);
    e.setBirthDate(LocalDate.of(1995, 9, 11));
    e.setPosition("Senior Engineer");
    e.setActive(true);

    EmployeeResponseDTO r = EmployeeMapper.toResponse(e);

    assertThat(r.getId()).isEqualTo(10L);
    assertThat(r.getFirstName()).isEqualTo("Jane");
    assertThat(r.getMiddleName()).isEqualTo("B");
    assertThat(r.getLastName()).isEqualTo("Doe");
    assertThat(r.getSecondLastName()).isEqualTo("Roe");
    assertThat(r.getAge()).isEqualTo(30);
    assertThat(r.getGender()).isEqualTo(Gender.F);
    assertThat(r.getBirthDate()).isEqualTo(LocalDate.of(1995, 9, 11));
    assertThat(r.getPosition()).isEqualTo("Senior Engineer");
    assertThat(r.getActive()).isTrue();
  }

  @Test
  void patchInto_updatesOnlyNonNulls() {
    Employee target = new Employee();
    target.setFirstName("John");
    target.setMiddleName("A");
    target.setLastName("Doe");
    target.setSecondLastName("Roe");
    target.setAge(28);
    target.setGender(Gender.F);
    target.setBirthDate(LocalDate.of(1997, 8, 5));
    target.setPosition("Engineer");
    target.setActive(true);

    EmployeePatchDTO patch = new EmployeePatchDTO();
    patch.setFirstName("Johnny");
    patch.setMiddleName(null);
    patch.setAge(29);
    patch.setPosition("Lead Engineer");

    EmployeeMapper.patchInto(target, patch);

    assertThat(target.getFirstName()).isEqualTo("Johnny");
    assertThat(target.getMiddleName()).isEqualTo("A");
    assertThat(target.getAge()).isEqualTo(29);
    assertThat(target.getPosition()).isEqualTo("Lead Engineer");
    assertThat(target.getLastName()).isEqualTo("Doe");
    assertThat(target.getSecondLastName()).isEqualTo("Roe");
    assertThat(target.getGender()).isEqualTo(Gender.F);
  }
}
