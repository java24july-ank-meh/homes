package com.revature.application.controllers;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.services.MaintenanceService;

@RestController
public class MaintenanceController
{
	@Autowired
	MaintenanceService maintenanceService;

	@GetMapping("Maintenance")
	public ResponseEntity<Object> displayAllMaintenanceRequest()
	{

		return ResponseEntity.ok(maintenanceService.findAll());

	}

}