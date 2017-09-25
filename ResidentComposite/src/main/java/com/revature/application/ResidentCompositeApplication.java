package com.revature.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
/*@EntityScan(
		  basePackageClasses = { ResidentCompositeApplication.class, Jsr310JpaConverters.class }
		)*/
@SpringBootApplication
public class ResidentCompositeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ResidentCompositeApplication.class, args);
	}
	
}
