
package com.demo.employees.infrastructure.interfaces.rest.resources;

import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.demo.employees.infrastructure.adapters.inbound.ApiPaths.EMPLOYEES;

@RequestMapping(EMPLOYEES)
public interface EmployeesResource {
    @GetMapping ResponseEntity<List<EmployeeResponseDTO>> getAll();
    @GetMapping("/{id}") ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id);
    @PostMapping ResponseEntity<List<EmployeeResponseDTO>> create(@Valid @RequestBody List<EmployeeRequestDTO> request);
    @PutMapping("/{id}") ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDTO request);
    @PatchMapping("/{id}") ResponseEntity<EmployeeResponseDTO> patch(@PathVariable Long id, @RequestBody EmployeePatchDTO patch);
    @DeleteMapping("/{id}") ResponseEntity<Void> delete(@PathVariable Long id);
    @GetMapping("/search") ResponseEntity<List<EmployeeResponseDTO>> search(@RequestParam("name") String name);
}
