
package com.demo.employees.infrastructure.adapters.inbound.handlers.employee;

import com.demo.employees.domain.entities.Employee;
import com.demo.employees.domain.exceptions.NotFoundException;
import com.demo.employees.domain.usecases.employee.CreateEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.DeleteEmployeeUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeeByIdUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.SearchEmployeesByNameUseCase;
import com.demo.employees.domain.usecases.employee.UpdateEmployeeUseCase;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeePatchDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeRequestDTO;
import com.demo.employees.infrastructure.adapters.inbound.dtos.EmployeeResponseDTO;
import com.demo.employees.infrastructure.adapters.inbound.mappers.EmployeeMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.demo.employees.domain.exceptions.ErrorCode.EMPLOYEE_NOT_FOUND;

@Component
public class EmployeeCommandHandler {

    private final CreateEmployeesUseCase createUC;
    private final GetEmployeesUseCase getAllUC;
    private final GetEmployeeByIdUseCase getByIdUC;
    private final UpdateEmployeeUseCase updateUC;
    private final DeleteEmployeeUseCase deleteUC;
    private final SearchEmployeesByNameUseCase searchUC;

    public EmployeeCommandHandler(
            CreateEmployeesUseCase createUC,
            GetEmployeesUseCase getAllUC,
            GetEmployeeByIdUseCase getByIdUC,
            UpdateEmployeeUseCase updateUC,
            DeleteEmployeeUseCase deleteUC,
            SearchEmployeesByNameUseCase searchUC) {
        this.createUC = createUC; this.getAllUC = getAllUC; this.getByIdUC = getByIdUC;
        this.updateUC = updateUC; this.deleteUC = deleteUC; this.searchUC = searchUC;
    }

    public List<EmployeeResponseDTO> create(List<EmployeeRequestDTO> reqs){
        List<Employee> created = createUC.create(reqs.stream().map(EmployeeMapper::toDomain).collect(Collectors.toList()));
        return created.stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
    }
    public List<EmployeeResponseDTO> getAll(){
        return getAllUC.getAll().stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
    }
    public EmployeeResponseDTO getById(Long id){
        return getByIdUC.getById(id)
                .map(EmployeeMapper::toResponse)
                .orElseThrow(() ->
                        new NotFoundException(EMPLOYEE_NOT_FOUND, "Employee " + id + " not found"));
    }
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO req){
        Employee updated = updateUC.update(id, EmployeeMapper.toDomain(req));
        return EmployeeMapper.toResponse(updated);
    }

    public EmployeeResponseDTO updatePartial(Long id, EmployeePatchDTO patch){
        Employee current = getByIdUC.getById(id)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND, "Employee " + id + " not found"));
        EmployeeMapper.patchInto(current, patch);
        Employee saved = updateUC.update(id, current);
        return EmployeeMapper.toResponse(saved);
    }
    public void delete(Long id){ deleteUC.delete(id); }
    public List<EmployeeResponseDTO> search(String name){
        return searchUC.search(name).stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
    }
}
