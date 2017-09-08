package com.revature.application.controllers;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.models.Maintenance;
import com.revature.application.services.MaintenanceService;

@RestController
public class MaintenanceController
{
	@Autowired
	MaintenanceService maintenanceService;

	@GetMapping("maintenance")
	public ResponseEntity<Object> displayAllMaintenanceRequest()
	{
		return ResponseEntity.ok(maintenanceService.findAll());
	}

	@GetMapping("units/{unitId}/maintenance")
	public ResponseEntity<Object> displayAllFromApartment(@PathVariable("unitId") int unitId)
	{
		
		List<Maintenance>maintenanceRequests = maintenanceService.findByUnitId(unitId);
		
		if(maintenanceRequests.isEmpty())
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("No Maintenance Requests found for unit");
		
		return ResponseEntity.ok(maintenanceRequests);
	}
	
	@RequestMapping(value="units/{unitId}/maintenance", method=RequestMethod.POST)
	public ResponseEntity<Object> createMaintanenceRequest(@PathVariable("unitId") int unitId, @RequestBody Maintenance maintenance)
	{
		return ResponseEntity.status(HttpStatus.SC_CREATED).body(maintenanceService.save(maintenance));
	}
	
	@RequestMapping(value="maintenance/{maintenanceId}")
	public ResponseEntity<Object> displayMaintenance(@PathVariable("maintenanceId") int maintenanceId)
	{
		Maintenance maintenance = maintenanceService.findById(maintenanceId);
		
		if(maintenance == null)
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Maintenance not found");
	
		return ResponseEntity.ok(maintenance);
	}
	
	@RequestMapping(value="maintenance/{maintenanceId}/complete", method=RequestMethod.POST)
	public ResponseEntity<Object> completeMaintenance(@PathVariable("maintenanceId") int maintenanceId)
	{
		Maintenance maintenance = maintenanceService.findById(maintenanceId);
		
		if(maintenance == null)
			return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Maintenance not found");
	
		maintenance.setResolved(true);
		
		return ResponseEntity.status(HttpStatus.SC_CREATED).body(maintenanceService.update(maintenance));
	}
}