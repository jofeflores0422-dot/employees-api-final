package com.demo.employees.infrastructure.adapters.inbound.dtos;

import com.demo.employees.domain.model.Gender;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static com.demo.employees.domain.shared.DomainConstraints.AGE_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.AGE_MIN;
import static com.demo.employees.domain.shared.DomainConstraints.FIRST_NAME_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.LAST_NAME_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.MIDDLE_NAME_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.POSITION_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.SECOND_LAST_NAME_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.SEX;
import static com.demo.employees.infrastructure.adapters.inbound.ApiConstants.DATE_PATTERN_DD_MM_YYYY;

public class EmployeeRequestDTO {

    @NotBlank
    @Size(max = FIRST_NAME_MAX)
    private String firstName;

    @Size(max = MIDDLE_NAME_MAX)
    private String middleName;

    @NotBlank
    @Size(max = LAST_NAME_MAX)
    private String lastName;

    @NotBlank
    @Size(max = SECOND_LAST_NAME_MAX)
    private String secondLastName;

    @NotNull
    @Min(AGE_MIN)
    @Max(AGE_MAX)
    private Integer age;

    @NotNull
    private Gender gender;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN_DD_MM_YYYY)
    private LocalDate birthDate;

    @NotBlank
    @Size(max = POSITION_MAX)
    private String position;

    private Boolean active;

    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String v) { this.middleName = v; }

    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }

    public String getSecondLastName() { return secondLastName; }
    public void setSecondLastName(String v) { this.secondLastName = v; }

    public Integer getAge() { return age; }
    public void setAge(Integer v) { this.age = v; }

    public Gender getGender() { return gender; }
    public void setGender(Gender v) { this.gender = v; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate v) { this.birthDate = v; }

    public String getPosition() { return position; }
    public void setPosition(String v) { this.position = v; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean v) { this.active = v; }
}