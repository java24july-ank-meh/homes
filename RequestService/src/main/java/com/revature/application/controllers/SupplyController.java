package com.revature.application.controllers;

import java.util.List;

//import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.models.Supply;
import com.revature.application.services.SupplyService;

import io.swagger.annotations.ApiOperation;

@RestController
public class SupplyController {

	@Autowired
	SupplyService supplyService;
	
	@GetMapping("supply")
	public ResponseEntity<Object> displayAllSupplyRequests(){
		return ResponseEntity.ok(supplyService.findAll());
	}
	
	@GetMapping("units/{unitId}/supply")
	public ResponseEntity<Object> displayAllFromUnit(@PathVariable("unitId") int unitId){
		
		List<Supply> supplyRequests = supplyService.findByUnitId(unitId);
		
		if(supplyRequests.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("No maintenance requests found for unit");
		}
		
		return ResponseEntity.ok(supplyRequests);
	}
	@ApiOperation(value = "Create Supply requests.")
	@RequestMapping(value = "units/{unitId}/supply", method=RequestMethod.POST)
	public ResponseEntity<Object> createSupplyRequest(@PathVariable("unitId") int unitId, @RequestBody List<Supply> supplies){
		
		for(Supply supply: supplies)
			supplyService.save(supply);
		
		return ResponseEntity.status(HttpStatus.CREATED/*HttpStatus.SC_CREATED*/).body("created");
	}
	
	@GetMapping(value ="supply/{supplyId}")
	public ResponseEntity<Object> displaySupply(@PathVariable("supplyId") int supplyId){
		Supply supply = supplyService.findById(supplyId);
		
		if(supply == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("Supply not found");
		}
		
		return ResponseEntity.ok(supply);
	}
	
	@RequestMapping(value = "supply/{supplyId}/complete", method = RequestMethod.POST)
	public ResponseEntity<Object> completeSupplyRequest(@PathVariable("supplyId") int supplyId){
		Supply supply = supplyService.findById(supplyId);
		
		if(supply == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("Supply not found");
		}
		
		supply.setResolved(true);
		
		return ResponseEntity.status(HttpStatus.CREATED/*HttpStatus.SC_CREATED*/).body(supplyService.update(supply));
	}
}
