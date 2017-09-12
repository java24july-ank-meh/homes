package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("resident")
public class ResidentController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping("invite")
	public  ResponseEntity<Object> invite(String token, String email, String firstName, String lastName){
	String requestUrl = "https://slack.com/api/users.admin.invite?token=" +
			token +"&email=" + email +
	"&first_name=" + firstName + "&last_name=" + lastName;

	String responseString = restTemplate.getForObject(requestUrl, String.class);
	
	return ResponseEntity.ok(responseString);
	}

	
}
