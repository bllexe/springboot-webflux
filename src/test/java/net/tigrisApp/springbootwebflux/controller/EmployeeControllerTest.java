package net.tigrisApp.springbootwebflux.controller;

import net.tigrisApp.springbootwebflux.dto.EmployeeDto;
import net.tigrisApp.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("save employee operation")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given --precondition operation

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));


        //when --action that we are going test

       WebTestClient.ResponseSpec response= webTestClient
                .post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto),EmployeeDto.class)
                .exchange();

        //then verify the output
        response .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }


    @Test
    @DisplayName("get  employee by id  operation")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

        //given --precondition operation
        String employeeId="123";
        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bilal@mail.com");

        BDDMockito.given(employeeService.getEmployee(employeeId))
                .willReturn(Mono.just(employeeDto));


        //when --action that we are going test
        WebTestClient.ResponseSpec response= webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id",employeeId))
                .exchange();

        //then verify the output
        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }


    @Test
    @DisplayName("get all employee operation")
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() {

        //given --precondition operation
        List<EmployeeDto>  list=new ArrayList<>();
        EmployeeDto employeeDto1=new EmployeeDto();
        employeeDto1.setFirstName("bilal");
        employeeDto1.setLastName("yakut");
        employeeDto1.setEmail("bilal@mail.com");
        list.add(employeeDto1);

        EmployeeDto employeeDto2=new EmployeeDto();
        employeeDto2.setFirstName("biilly");
        employeeDto2.setLastName("billy");
        employeeDto2.setEmail("billy@mail.com");
        list.add(employeeDto2);

        Flux<EmployeeDto> employeeDtoFlux=Flux.fromIterable(list);

        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeDtoFlux);

        //when --action that we are going test
        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        //then verify the output

        response.expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);


    }



    @Test
    @DisplayName("update employee operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {

        //given --precondition operation
        String employeeId="123";

        EmployeeDto employeeDto=new EmployeeDto();
        employeeDto.setFirstName("bilal");
        employeeDto.setLastName("yakut");
        employeeDto.setEmail("bllexe@mail.com");

        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),
                        ArgumentMatchers.any(String.class)))
                .willReturn(Mono.just(employeeDto));

        //when --action that we are going test

      WebTestClient.ResponseSpec response= webTestClient.put().uri("/api/employees/{id}",Collections.singletonMap("id",employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto),EmployeeDto.class)
                .exchange();

        //then verify the output

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());


    }


    @Test
    @DisplayName("delete employee operation")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {

        //given --precondition operation
        String employeeId ="12345";
        Mono<Void> voidMono=Mono.empty();
        BDDMockito.given(employeeService.deleteEmployee(employeeId)).willReturn(voidMono);

        //when --action that we are going test

        WebTestClient.ResponseSpec response= webTestClient.delete()
                .uri("/api/employees/{id}",Collections.singletonMap("id",employeeId))
                .exchange();

        //then verify the output
        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);

    }

}