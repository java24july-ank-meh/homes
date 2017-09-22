package com.revature.application.controller;

import java.net.URI;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.model.Associate;
import com.revature.application.service.ProfileCompositeService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@RestController
@RequestMapping("profilecomposite")
public class ProfileCompositeController {
	
	@Autowired
	ProfileCompositeService profileCService;
	
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
		
		/*
		 * maybe this will work?
		 */
		
		JsonObject obj1 = jsonReturned("associates", id);
		
		return ResponseEntity.ok(compositeObj.toString());
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
		
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8090/associates");
//		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
	
	/*
	 * maybe this will work? 
	 */

}
