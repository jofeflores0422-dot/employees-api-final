
package com.demo.employees.infrastructure.interfaces.rest;

import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import com.demo.employees.infrastructure.adapters.inbound.handlers.employee.EmployeeCommandHandler;
import com.demo.employees.infrastructure.interfaces.rest.resources.EmployeesResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EmployeesController implements EmployeesResource {

    private final EmployeeCommandHandler handler;

    public EmployeesController(EmployeeCommandHandler handler) { this.handler = handler; }

    @Override public ResponseEntity<List<EmployeeResponseDTO>> getAll(){
        return ResponseEntity.ok(handler.getAll());
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> getById(Long id) {
        return ResponseEntity.ok(handler.getById(id));
    }

    @Override public ResponseEntity<List<EmployeeResponseDTO>> create(List<EmployeeRequestDTO> request){
        List<EmployeeResponseDTO> created = handler.create(request);
        return ResponseEntity.created(UriComponentsBuilder.fromPath("/employees").build().toUri()).body(created);
    }

    @Override public ResponseEntity<EmployeeResponseDTO> update(Long id, EmployeeRequestDTO request){
        return ResponseEntity.ok(handler.update(id, request));
    }

    @Override
    public ResponseEntity<EmployeeResponseDTO> patch(Long id, EmployeePatchDTO patch) {
        return ResponseEntity.ok(handler.updatePartial(id, patch));
    }

    @Override public ResponseEntity<Void> delete(Long id){
        handler.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override public ResponseEntity<List<EmployeeResponseDTO>> search(String name){
        return ResponseEntity.ok(handler.search(name));
    }
}
