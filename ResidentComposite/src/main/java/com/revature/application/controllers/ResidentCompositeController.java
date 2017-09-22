package com.revature.application.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import com.revature.application.model.Associate;
import com.revature.application.model.ResidentUnitComplexOffice;
import com.revature.application.model.Unit;

@RestController
@RequestMapping("residentcomposite")
public class ResidentCompositeController {

	private final String ASSOCIATESERV = "http://localhost:8090/";
	private final String COMPLEXSERV = "http://localhost:8093/";

	public JsonElement getJsonFromService(String url) {
		Client client = Client.create();
		WebResource webResource = client.resource(url);
		String response = webResource.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("WEB RESOURCE RESPONSE: "+response);
		return new JsonParser().parse(response);
	}

	@GetMapping(value = "residentinfo")
	public ResponseEntity<Object> getResidentInfo() {
		Gson gson = new Gson();

		//		Associate[] jsonArr = gson.fromJson(getJsonFromService(ASSOCIATESERV+"associates"), Associate[].class);
		Associate[] jsonArr = gson.fromJson(jsonReturned("associates", ""), );

		return new ResponseEntity<Object>(jsonArr, HttpStatus.OK);
	}

	@GetMapping("residentinfo/withRoomDetails")
	public ResponseEntity<Object> findAllAssociatesWithRoomDetails() {
		Gson gson = new Gson();
		Associate[] associates = gson.fromJson(getJsonFromService(ASSOCIATESERV+"associates"), Associate[].class);

		List<ResidentUnitComplexOffice> residentInfoList = new ArrayList<>();

		for(Associate associate : associates) {
			ResidentUnitComplexOffice residentInfo = new ResidentUnitComplexOffice();
			residentInfo.setAssociate(associate);
			residentInfo.setUnit(
					gson.fromJson(getJsonFromService(COMPLEXSERV+"unit/"+associate.getUnitId().toString()), Unit.class)
					);
			if (residentInfo.getUnit() != null) {
				residentInfo.setComplex(residentInfo.getUnit().getComplex());
				if (residentInfo.getComplex() != null)
					residentInfo.setOffice(residentInfo.getUnit().getComplex().getOffice());
			}

			residentInfoList.add(residentInfo);
		}

		return ResponseEntity.ok(residentInfoList);
	}


	private JsonObject jsonReturned(String endpoint1, String endpoint2) {
		//for consuming a rest service
		ClientConfig config = new ClientConfig();
		javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getRestServiceURI());
		
		String associate = null;
		
		if(endpoint2.isEmpty()) {
			associate = target.path(endpoint1).request().accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);	
		} else {	
			associate = target.path(endpoint1).path(endpoint2).request().accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		}

		return new JsonParser().parse(associate).getAsJsonObject();
	}

	private static URI getRestServiceURI() {

		String loc = "http://localhost:8085";
		String site = "/api";//idk if this is right..?

		return UriBuilder.fromUri(loc+site).build();
	}

}