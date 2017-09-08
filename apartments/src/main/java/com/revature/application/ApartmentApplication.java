package com.revature.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

@RestController
@SpringBootApplication
public class ApartmentApplication {
	@Autowired
	OfficeService os;
	
	public static void main(String[] args) {
		SpringApplication.run(ApartmentApplication.class, args);
	}
	
	@RequestMapping(value="/")
	public String helloWorld() {
		return "Hello World!";
	}
}
