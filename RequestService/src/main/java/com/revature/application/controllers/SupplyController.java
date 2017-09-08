package com.revature.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.services.SupplyService;

@RestController

public class SupplyController {

	@Autowired
	SupplyService supplyService;
	
	@GetMapping("Supply")
	public ResponseEntity<Object> displayAllSupplyRequests(){
		return ResponseEntity.ok(supplyService.findAll());
	}
	
}
