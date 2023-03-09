package net.tigrisApp.springbootwebflux.service.impl;

import lombok.AllArgsConstructor;
import net.tigrisApp.springbootwebflux.dto.EmployeeDto;
import net.tigrisApp.springbootwebflux.entity.Employee;
import net.tigrisApp.springbootwebflux.mapper.EmployeeMaper;
import net.tigrisApp.springbootwebflux.repository.EmployeeRepository;
import net.tigrisApp.springbootwebflux.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {



    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {

        //convert employeeDto to Employee for saving in repository
        Employee employee = EmployeeMaper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);

       return savedEmployee
               .map((employeeEntity)->EmployeeMaper.mapToEmployeeDto(employeeEntity));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {

       return employeeRepository.findById(employeeId).map(employee -> EmployeeMaper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        Flux<Employee> employeeList = employeeRepository.findAll();

        return employeeList
                 .map((employee)-> EmployeeMaper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {

        Mono<Employee> employeeDb = employeeRepository.findById(employeeId);
        if (employeeDb==null){
            throw  new RuntimeException("Employee Does not exist");
        }
         Mono<Employee> updateEmployee=  employeeDb.flatMap((existEmployee)->{
            existEmployee.setFirstName(employeeDto.getFirstName());
            existEmployee.setLastName(employeeDto.getLastName());
            existEmployee.setEmail(employeeDto.getEmail());

           return employeeRepository.save(existEmployee);
        });

        return updateEmployee.map((emp)->EmployeeMaper.mapToEmployeeDto(emp));

    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {

      return employeeRepository.deleteById(employeeId);

    }
}
