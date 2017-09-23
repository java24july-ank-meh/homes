package com.revature.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.service.ComplexCompositeService;

@RestController
@RequestMapping("complexcomposite")
public class ComplexCompositeController {
	
	@Autowired
	ComplexCompositeService compositeService;
	
	
	//this is for getting the big list of complexes
	@GetMapping
	public ResponseEntity<Object> getComplexes() {
		return ResponseEntity.ok(compositeService.getAllComplexes().toString());
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
