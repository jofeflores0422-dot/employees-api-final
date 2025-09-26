package com.demo.employees.unit.tests.domain.entities;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.model.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {

  @Test
  void builderSettersAndGettersWork() {
    Employee e = new Employee();
    e.setId(1L);
    e.setFirstName("John");
    e.setMiddleName("A");
    e.setLastName("Doe");
    e.setSecondLastName("Roe");
    e.setAge(28);
    e.setGender(Gender.F);
    e.setBirthDate(LocalDate.of(1997, 8, 5));
    e.setPosition("Engineer");
    e.setActive(true);

    assertThat(e.getId()).isEqualTo(1L);
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
  void equalsAndHashCode_sameId_areEqual() {
    Employee e1 = new Employee();
    e1.setId(1L);
    Employee e2 = new Employee();
    e2.setId(1L);

    assertThat(e1).isEqualTo(e2);
    assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
  }

  @Test
  void equalsAndHashCode_differentId_areNotEqual() {
    Employee e1 = new Employee();
    e1.setId(1L);
    Employee e2 = new Employee();
    e2.setId(2L);

    assertThat(e1).isNotEqualTo(e2);
    assertThat(e1.hashCode()).isNotEqualTo(e2.hashCode());
  }

  @Test
  void equals_sameObject_returnsTrue() {
    Employee e = new Employee();
    e.setId(1L);

    assertThat(e).isEqualTo(e);
  }

  @Test
  void equals_differentType_returnsFalse() {
    Employee e = new Employee();
    e.setId(1L);

    assertThat(e.equals("not-an-employee")).isFalse();
  }

  @Test
  void equals_nullIds_areEqual() {
    Employee e1 = new Employee();
    Employee e2 = new Employee();

    e1.setId(null);
    e2.setId(null);

    assertThat(e1).isEqualTo(e2);
    assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
  }
}