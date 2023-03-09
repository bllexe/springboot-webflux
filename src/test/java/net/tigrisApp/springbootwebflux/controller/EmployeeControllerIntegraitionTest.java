package net.tigrisApp.springbootwebflux.controller;


import net.tigrisApp.springbootwebflux.dto.EmployeeDto;
import net.tigrisApp.springbootwebflux.repository.EmployeeRepository;
import net.tigrisApp.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegraitionTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before(){
        employeeRepository.deleteAll().subscribe();
    }
    @Test
    public void testSaveEmployee() {

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto),EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }


    @Test
    public void testGetSingleEmployee() {
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }


    @Test
    public void testGetAllEmployees(){

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        EmployeeDto employeeDto2=new EmployeeDto();
        employeeDto2.setFirstName("jenny");
        employeeDto2.setLastName("jenny");
        employeeDto2.setEmail("jenny@mail.com");

        employeeService.saveEmployee(employeeDto).block();
        employeeService.saveEmployee(employeeDto2).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);

    }

    @Test
    public void testUpdateEmployee(){

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updatedEmployee=new EmployeeDto();
        employeeDto.setFirstName("izzy");
        employeeDto.setLastName("izzy");
        employeeDto.setEmail("izzy@mail.com");

        webTestClient.put().uri("/api/employees/{id}",Collections.singletonMap("id",savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee),EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());

    }

    @Test
    public void testDeleteEmployee(){

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();
        webTestClient.delete().uri("/api/employees/{id}",Collections.singletonMap("id",savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);



    }

}
