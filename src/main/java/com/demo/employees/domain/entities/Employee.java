package com.demo.employees.domain.entities;

import com.demo.employees.domain.model.Gender;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private Integer age;
    private Gender gender;
    private LocalDate birthDate;
    private String position;
    private Boolean active;

    public Employee() {}

    public Employee(Long id, String firstName, String middleName, String lastName, String secondLastName,
                    Integer age, Gender gender, LocalDate birthDate, String position, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.age = age;
        this.gender = gender;
        this.birthDate = birthDate;
        this.position = position;
        this.active = active;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSecondLastName() { return secondLastName; }
    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Override public boolean equals(Object o){ if(this==o) return true; if(!(o instanceof Employee)) return false;
        Employee e=(Employee)o; return Objects.equals(id,e.id); }
    @Override public int hashCode(){ return Objects.hash(id); }
}
