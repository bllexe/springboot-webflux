package net.tigrisApp.springbootwebflux.mapper;

import net.tigrisApp.springbootwebflux.dto.EmployeeDto;
import net.tigrisApp.springbootwebflux.entity.Employee;

public class EmployeeMaper {

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(
          employee.getId(),
          employee.getFirstName(),
          employee.getLastName(),
          employee.getEmail()
        );
    }

    public static  Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
        employeeDto.getId(),
        employeeDto.getFirstName(),
        employeeDto.getLastName(),
        employeeDto.getEmail()
        );
    }
}
