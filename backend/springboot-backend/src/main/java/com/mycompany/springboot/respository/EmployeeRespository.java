package com.mycompany.springboot.respository;

import com.mycompany.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRespository extends JpaRepository<Employee, Long> {
    // All CRUD methods


}
