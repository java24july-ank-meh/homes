package com.revature.application.controller;

import java.net.URI;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.service.ProfileCompositeService;


@RestController
@RequestMapping("profilecomposite")
public class ProfileCompositeController {

	@Autowired
	ProfileCompositeService profileCService;

	@GetMapping("{id}")
	public ResponseEntity<Object> getProfileInfo(@PathVariable("id") String id) {
	/*	JsonObject compositeObj = getJsonFromService("http://localhost:8090/associates/" +id);		
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit/" +compositeObj.get("unitId").getAsString());
		JsonObject complexJson = getJsonFromService("http://localhost:8093/complex/" +compositeObj.get("complexId").getAsString());*/	
		
		JsonObject obj1 = jsonReturned("associates", id);
		JsonObject unitJson = jsonReturned("unit", obj1.get("unitId").getAsString());
		JsonObject complexJson = jsonReturned("complex", obj1.get("complexId").getAsString());
		
		//profile needs to know complex name
//		compositeObj.add("complexName", complexJson.get("name")); 
		obj1.add("complexName", complexJson.get("name")); 
		
		// profile needs to know room number
//		compositeObj.add("unitName", unitJson.get("unitNumber"));
		obj1.add("unitName", unitJson.get("unitNumber"));

		// profile needs to know id for routing
//		compositeObj.add("unitId", unitJson.get("unitId"));
		obj1.add("unitId", unitJson.get("unitId"));

		return ResponseEntity.ok(obj1.toString());
	}
	/*
	 * so they moved sun jersey to glassfish jersey, check dependencies and mvnrepository.com
	 */
	private JsonObject getJsonFromService(String url) {
		//for consuming a rest service
		ClientConfig config = new ClientConfig();
		javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getRestServiceURI());
		String associate = target.path(url).request().accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}

	
	
	private JsonObject jsonReturned(String endpoint1, String endpoint2) {
		//for consuming a rest service
		ClientConfig config = new ClientConfig();
		javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getRestServiceURI());
		String associate = target.path(endpoint1).path(endpoint2).request().accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}

	private static URI getRestServiceURI() {

		String loc = "http://localhost:8085";
		String site = "/api";//idk if this is right..?

		return UriBuilder.fromUri(loc+site).build();
	}

}
