package com.revature.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.revature.zuul.filters.ErrorFilter;
import com.revature.zuul.filters.PreFilter;

@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
	//Filter for logging and "filtered" parameter injection. 
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	/* Commenting out filters in case that is causing the bugs.
	@Bean 
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	*/
}
