package com.mycompany.springboot.controller;

import com.mycompany.springboot.exception.ResourceNotFoundException;
import com.mycompany.springboot.model.Employee;
import com.mycompany.springboot.respository.EmployeeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRespository employeeRespository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRespository.findAll();
    }

    // Create Employee REST API using POST Method
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRespository.save(employee);
    }


    // GET Employee by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = employeeRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Exist woth id:" + id));
        return ResponseEntity.ok(employee);
    }

    //Update Employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){
        Employee updateEmployee = employeeRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee with id "+id+"do not exist"));
        updateEmployee.setFirstname(employeeDetails.getFirstname());
        updateEmployee.setLastname(employeeDetails.getLastname());
        updateEmployee.setEmail(employeeDetails.getEmail());
        employeeRespository.save(updateEmployee);
        return ResponseEntity.ok(updateEmployee);
    }

    //Delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        Employee employee = employeeRespository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee not exist with id :"+"id"));
        employeeRespository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
