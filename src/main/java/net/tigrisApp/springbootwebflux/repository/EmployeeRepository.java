package net.tigrisApp.springbootwebflux.repository;

import net.tigrisApp.springbootwebflux.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<Employee,String> {


}
