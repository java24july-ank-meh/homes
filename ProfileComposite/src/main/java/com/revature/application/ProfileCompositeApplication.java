package com.revature.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProfileCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileCompositeApplication.class, args);
	}
}
