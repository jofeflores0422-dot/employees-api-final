package com.demo.employees.infrastructure.adapters.inbound.dtos;

import com.demo.employees.domain.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.demo.employees.domain.shared.DomainConstraints.AGE_MAX;
import static com.demo.employees.domain.shared.DomainConstraints.AGE_MIN;
import static com.demo.employees.infrastructure.adapters.inbound.ApiConstants.DATE_PATTERN_DD_MM_YYYY;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeePatchDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;

    @Min(AGE_MIN) @Max(AGE_MAX)
    private Integer age;

    @Pattern(regexp="(?i)^[MFO]$")
    private Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN_DD_MM_YYYY)
    private LocalDate birthDate;

    private String position;
    private Boolean active;

    public String getFirstName(){return firstName;} public void setFirstName(String v){this.firstName=v;}
    public String getMiddleName(){return middleName;} public void setMiddleName(String v){this.middleName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){this.lastName=v;}
    public String getSecondLastName(){return secondLastName;} public void setSecondLastName(String v){this.secondLastName=v;}
    public Integer getAge(){return age;} public void setAge(Integer v){this.age=v;}
    public Gender getGender(){return gender;} public void setGender(Gender v){this.gender=v;}
    public LocalDate getBirthDate(){return birthDate;} public void setBirthDate(LocalDate v){this.birthDate=v;}
    public String getPosition(){return position;} public void setPosition(String v){this.position=v;}
    public Boolean getActive(){return active;} public void setActive(Boolean v){this.active=v;}
}
