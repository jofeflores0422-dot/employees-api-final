
package com.demo.employees.infrastructure.adapters.outbound.repositories.jpa;

import com.demo.employees.domain.model.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 60)
    private String firstName;

    @Column(name = "SECOND_NAME", length = 60)
    private String middleName;

    @Column(name = "LAST_NAME", nullable = false, length = 80)
    private String lastName;

    @Column(name = "MOTHER_LAST_NAME", nullable = false, length = 80)
    private String secondLastName;

    @Column(name = "AGE", nullable = false, precision = 3, scale = 0)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER", nullable = false, length = 1)
    private Gender gender;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Column(name = "POSITION", nullable = false, length = 120)
    private String position;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "CREATED_AT", nullable = false,
            insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean v) { this.active = v; }
}