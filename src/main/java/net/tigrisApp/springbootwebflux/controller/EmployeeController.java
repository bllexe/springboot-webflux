package net.tigrisApp.springbootwebflux.controller;

import lombok.AllArgsConstructor;
import net.tigrisApp.springbootwebflux.dto.EmployeeDto;
import net.tigrisApp.springbootwebflux.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("{employeeId}")
    public Mono<EmployeeDto> getEmployee(@PathVariable String employeeId){

       return employeeService.getEmployee(employeeId);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,@PathVariable("id") String id){

        return employeeService.updateEmployee(employeeDto,id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteEmployee(@PathVariable("id") String id){
        return employeeService.deleteEmployee(id);

    }
}
