package com.demo.employees.infrastructure.adapters.inbound.mappers;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;

public class EmployeeMapper {

    public static Employee toDomain(EmployeeRequestDTO d){
        Employee e = new Employee();
        e.setFirstName(d.getFirstName());
        e.setMiddleName(d.getMiddleName());
        e.setLastName(d.getLastName());
        e.setSecondLastName(d.getSecondLastName());
        e.setAge(d.getAge());
        e.setGender(d.getGender());
        e.setBirthDate(d.getBirthDate());
        e.setPosition(d.getPosition());
        e.setActive(d.getActive());
        return e;
    }

    public static EmployeeResponseDTO toResponse(Employee e){
        EmployeeResponseDTO r = new EmployeeResponseDTO();
        r.setId(e.getId());
        r.setFirstName(e.getFirstName());
        r.setMiddleName(e.getMiddleName());
        r.setLastName(e.getLastName());
        r.setSecondLastName(e.getSecondLastName());
        r.setAge(e.getAge());
        r.setGender(e.getGender());
        r.setBirthDate(e.getBirthDate());
        r.setPosition(e.getPosition());
        r.setActive(e.getActive());
        return r;
    }

    public static void patchInto(Employee target, EmployeePatchDTO p) {
        if (p == null || target == null) return;
        if (p.getFirstName() != null)      target.setFirstName(p.getFirstName());
        if (p.getMiddleName() != null)     target.setMiddleName(p.getMiddleName());
        if (p.getLastName() != null)       target.setLastName(p.getLastName());
        if (p.getSecondLastName() != null) target.setSecondLastName(p.getSecondLastName());
        if (p.getAge() != null)            target.setAge(p.getAge());
        if (p.getGender() != null)         target.setGender(p.getGender());
        if (p.getBirthDate() != null)      target.setBirthDate(p.getBirthDate());
        if (p.getPosition() != null)       target.setPosition(p.getPosition());
        if (p.getActive() != null)         target.setActive(p.getActive());
    }
}
