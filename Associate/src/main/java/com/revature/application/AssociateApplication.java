package com.revature.application;

import javax.xml.transform.Source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;

@SpringBootApplication
@EnableBinding(Source.class)
public class AssociateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssociateApplication.class, args);
	}
	
}
