package com.revature.application.controller;

import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.model.Associate;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@RestController
@RequestMapping("profilecomposite")
public class ProfileCompositeController {
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getProfileInfo(@PathVariable("id") String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8090/associates/" +id);
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit/" +compositeObj.get("unitId").getAsString());
		JsonObject complexJson = getJsonFromService("http://localhost:8093/complex/" +compositeObj.get("complexId").getAsString());
		
		//profile needs to know complex name
		compositeObj.add("complexName", complexJson.get("name")); 
		// profile needs to know room number
		compositeObj.add("unitName", unitJson.get("unitNumber"));
		// profile needs to know id for routing
		compositeObj.add("unitId", unitJson.get("unitId"));
		return ResponseEntity.ok(compositeObj.toString());
	}
	
	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8090/associates");
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
}
