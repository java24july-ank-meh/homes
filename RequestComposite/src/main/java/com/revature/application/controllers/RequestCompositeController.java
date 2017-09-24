package com.revature.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.services.RequestCompositeService;

@RestController
@RequestMapping("requestcomposite")
public class RequestCompositeController {
	
	@Autowired
	RequestCompositeService requestService;
	
	@PostMapping("{id}")
	public ResponseEntity<Object> sendMessage(@PathVariable("id") int id, @RequestBody String message) {
		
		requestService.sendMessage(id, message);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("maintenances/{id}")
	public ResponseEntity<Object> getMaintenanceRequests(@PathVariable("id") int id) {
		return ResponseEntity.ok(requestService.getMaintenanceRequest(id));
	}
	
	@GetMapping("supplies/{id}")
	public ResponseEntity<Object> getSupplyRequests(@PathVariable("id") int id) {
		return ResponseEntity.ok(requestService.getSupplyRequest(id));
	}
	
	@GetMapping("maintenances")
	public ResponseEntity<Object> getAllMaintenanceRequests() {
		return ResponseEntity.ok(requestService.getAllMaintenanceRequests());
	}
	
	@GetMapping("supplies")
	public ResponseEntity<Object> getAllSupplyRequests() {
		return ResponseEntity.ok(requestService.getAllSupplyRequests());
	}
}
