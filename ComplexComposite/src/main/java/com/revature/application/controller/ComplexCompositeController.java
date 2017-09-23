package com.revature.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.model.Associate;
import com.revature.application.model.Complex;
import com.revature.application.service.ComplexCompositeService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import springfox.documentation.spring.web.json.Json;

@RestController
@RequestMapping("complexcomposite")
public class ComplexCompositeController {
	
	@Autowired
	ComplexCompositeService compositeService;
	
	
	//this is for getting the big list of complexes
	@GetMapping
	public String getComplexes() {
		return compositeService.allComplexes().toString();
	}
	
	@GetMapping("units")
	public String getUnits() {
		return compositeService.allUnits().toString();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getComplex(@PathVariable("id") String id) {		
		return ResponseEntity.ok(compositeService.getComplex(id).toString());
		
	}
	
	@PostMapping("reassignment")
	public ResponseEntity<Object> unitReassignmentRequest(@RequestBody String jsonString) {
		
		compositeService.unitReassignment(jsonString);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("{unitId}/assign/{associateId}")
	public ResponseEntity<Object> assignUnitToAssociate(@PathVariable("unitId") int unitId, @PathVariable("associateId") int associateId) {
		return ResponseEntity.ok(compositeService.assignUnitToAssociate(unitId, associateId));
	}
	
}
