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

import com.revature.application.models.Maintenance;
import com.revature.application.services.MaintenanceService;

import io.swagger.annotations.ApiOperation;

@RestController
public class MaintenanceController
{
	@Autowired
	MaintenanceService maintenanceService;

	@ApiOperation(value = "View all maintenance requests.")
	@GetMapping("maintenance")
	public ResponseEntity<Object> displayAllMaintenanceRequest()
	{
		return ResponseEntity.ok(maintenanceService.findAll());
	}

//	@ApiOperation(value = "View all maintenance requests for a single Unit.", response = Iterable.class)
	@GetMapping("units/{unitId}/maintenance")
	public ResponseEntity<Object> displayAllFromUnit(@PathVariable("unitId") int unitId)
	{
		
		List<Maintenance>maintenanceRequests = maintenanceService.findByUnitId(unitId);
		
		if(maintenanceRequests.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("No Maintenance Requests found for unit");
		
		return ResponseEntity.ok(maintenanceRequests);
	}
	
//	@ApiOperation(value = "Create Maintenance request for Unit.", response = Iterable.class)
	@RequestMapping(value="units/{unitId}/maintenance", method=RequestMethod.POST)
	public ResponseEntity<Object> createMaintanenceRequest(@PathVariable("unitId") int unitId, @RequestBody Maintenance maintenance)
	{
		return ResponseEntity.status(HttpStatus.CREATED/*HttpStatus.SC_CREATED*/).body(maintenanceService.save(maintenance));
	}
	
//	@ApiOperation(value = "View Maintenance Request By Maintenance Id.", response = Iterable.class)
	@GetMapping(value="maintenance/{maintenanceId}")
	public ResponseEntity<Object> displayMaintenance(@PathVariable("maintenanceId") int maintenanceId)
	{
		Maintenance maintenance = maintenanceService.findById(maintenanceId);
		
		if(maintenance == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("Maintenance not found");
	
		return ResponseEntity.ok(maintenance);
	}
	
//	@ApiOperation(value = "Complete Maintenance Request", response = Iterable.class)
	@RequestMapping(value="maintenance/{maintenanceId}/complete", method=RequestMethod.POST)
	public ResponseEntity<Object> completeMaintenance(@PathVariable("maintenanceId") int maintenanceId)
	{
		Maintenance maintenance = maintenanceService.findById(maintenanceId);
		
		if(maintenance == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND/*HttpStatus.SC_NOT_FOUND*/).body("Maintenance not found");
	
		maintenance.setResolved(true);
		
		return ResponseEntity.status(HttpStatus.CREATED/*HttpStatus.SC_CREATED*/).body(maintenanceService.update(maintenance));
	}
}