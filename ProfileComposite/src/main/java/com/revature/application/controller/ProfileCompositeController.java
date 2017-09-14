package com.revature.application.controller;

import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Associate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@RestController
public class ProfileCompositeController {
	
	@GetMapping("profile/{id}")
	public ResponseEntity<Object> getProfileInfo(@PathVariable("id") String id) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8090/associates");
		String associate = resource.path(id).accept(MediaType.APPLICATION_JSON).get(String.class);
		return ResponseEntity.ok(associate);
	}
}
