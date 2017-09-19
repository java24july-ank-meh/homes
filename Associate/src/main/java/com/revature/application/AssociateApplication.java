package com.revature.application;

import javax.xml.transform.Source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EnableDiscoveryClient
@EntityScan(
		  basePackageClasses = { AssociateApplication.class, Jsr310JpaConverters.class }
		)
@SpringBootApplication
public class AssociateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociateApplication.class, args);
	}
	
}
