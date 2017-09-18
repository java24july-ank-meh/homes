package com.revature.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@SpringBootApplication
public class ApartmentApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApartmentApplication.class, args);
	}
	
	@RequestMapping(value="/")
	public String helloWorld() {
		return "Hello World!";
	}
}
