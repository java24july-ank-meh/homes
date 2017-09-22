package com.revature.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
@SpringBootApplication
public class RequestCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestCompositeApplication.class, args);
	}
}
