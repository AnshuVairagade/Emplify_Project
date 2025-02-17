package com.mycompany.springboot;

import com.mycompany.springboot.model.Employee;
import com.mycompany.springboot.respository.EmployeeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBackendApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Autowired
	private EmployeeRespository employeeRespository;

	@Override
	public void run(String... args) throws Exception {
//		Employee employee = new Employee();
//		employee.setFirstname("gwen");
//		employee.setLastname("stacy");
//		employee.setEmail("gwenstacy@gmail.com");
//		employeeRespository.save(employee);

	}
}
