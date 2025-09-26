
package com.demo.employees.infrastructure.adapters.inbound.dtos;

import com.demo.employees.domain.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private Integer age;
    private Gender gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private String position;
    private OffsetDateTime createdAt;
    private Boolean active;

    // getters and setters
    public Long getId(){return id;} public void setId(Long v){this.id=v;}
    public String getFirstName(){return firstName;} public void setFirstName(String v){this.firstName=v;}
    public String getMiddleName(){return middleName;} public void setMiddleName(String v){this.middleName=v;}
    public String getLastName(){return lastName;} public void setLastName(String v){this.lastName=v;}
    public String getSecondLastName(){return secondLastName;} public void setSecondLastName(String v){this.secondLastName=v;}
    public Integer getAge(){return age;} public void setAge(Integer v){this.age=v;}
    public Gender getGender(){return gender;} public void setGender(Gender v){this.gender=v;}
    public LocalDate getBirthDate(){return birthDate;} public void setBirthDate(LocalDate v){this.birthDate=v;}
    public String getPosition(){return position;} public void setPosition(String v){this.position=v;}
    public OffsetDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(OffsetDateTime v){this.createdAt=v;}
    public Boolean getActive(){return active;} public void setActive(Boolean v){this.active=v;}
}
