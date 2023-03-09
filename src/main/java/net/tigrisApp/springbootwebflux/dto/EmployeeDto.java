package net.tigrisApp.springbootwebflux.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
