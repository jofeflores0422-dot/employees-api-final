
package com.demo.employees.infrastructure.config;

import com.demo.employees.business.ports.EmployeeRepositoryPort;
import com.demo.employees.business.usecases.employee.CreateEmployeesUseCaseImpl;
import com.demo.employees.business.usecases.employee.DeleteEmployeeUseCaseImpl;
import com.demo.employees.business.usecases.employee.GetEmployeeByIdUseCaseImpl;
import com.demo.employees.business.usecases.employee.GetEmployeesUseCaseImpl;
import com.demo.employees.business.usecases.employee.SearchEmployeesByNameUseCaseImpl;
import com.demo.employees.business.usecases.employee.UpdateEmployeeUseCaseImpl;
import com.demo.employees.domain.usecases.employee.CreateEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.DeleteEmployeeUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeeByIdUseCase;
import com.demo.employees.domain.usecases.employee.GetEmployeesUseCase;
import com.demo.employees.domain.usecases.employee.SearchEmployeesByNameUseCase;
import com.demo.employees.domain.usecases.employee.UpdateEmployeeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeBeansConfiguration {

    @Bean
    public CreateEmployeesUseCase createEmployeesUseCase(EmployeeRepositoryPort repo) {
        return new CreateEmployeesUseCaseImpl(repo);
    }

    @Bean
    public GetEmployeesUseCase getEmployeesUseCase(EmployeeRepositoryPort repo) {
        return new GetEmployeesUseCaseImpl(repo);
    }

    @Bean
    public GetEmployeeByIdUseCase getEmployeeByIdUseCase(EmployeeRepositoryPort repo) {
        return new GetEmployeeByIdUseCaseImpl(repo);
    }

    @Bean
    public UpdateEmployeeUseCase updateEmployeeUseCase(EmployeeRepositoryPort repo) {
        return new UpdateEmployeeUseCaseImpl(repo);
    }

    @Bean
    public DeleteEmployeeUseCase deleteEmployeeUseCase(EmployeeRepositoryPort repo) {
        return new DeleteEmployeeUseCaseImpl(repo);
    }

    @Bean
    public SearchEmployeesByNameUseCase searchEmployeesByNameUseCase(EmployeeRepositoryPort repo) {
        return new SearchEmployeesByNameUseCaseImpl(repo);
    }
}
