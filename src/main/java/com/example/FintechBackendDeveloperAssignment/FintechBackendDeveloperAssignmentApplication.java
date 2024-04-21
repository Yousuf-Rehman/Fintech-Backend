package com.example.FintechBackendDeveloperAssignment;

import com.example.FintechBackendDeveloperAssignment.conf.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = {"com.example.FintechBackendDeveloperAssignment"})
@SpringBootApplication
public class FintechBackendDeveloperAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintechBackendDeveloperAssignmentApplication.class, args);
	}

}
