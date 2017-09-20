package com.revature.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Unit;
import com.revature.application.service.ComplexService;
import com.revature.application.service.UnitService;

@RestController
@RequestMapping("/unit")
public class UnitController {

	@Autowired
	UnitService unitService;
	
	@Autowired
	ComplexService complexService;

	@GetMapping
	public Object displayAllUnit() {
		return unitService.findAll();
	}

	@GetMapping(value = "{id}")
	public Object displayUnit(@PathVariable("id") long id) {
		Unit unit = unitService.findByUnitId(id);
		if(unit == null) throw new ResourceNotFoundException();
		return unit;
	}
	
	@PostMapping
	public Object createUnit(@RequestBody Unit unit) {
		if(unit.getComplex() != null) {
			unit.setComplex(complexService.findByComplexId(unit.getComplex().getComplexId()));
		}
		return unitService.save(unit);
	}
	
	@PutMapping(value = "{id}")
	public Object updateUnit(@PathVariable("id") long id, @RequestBody Unit unit) {
		Unit u = unitService.findByUnitId(id);
		if(u == null) throw new ResourceNotFoundException();
		if(unit.getCapacity() != 0) u.setCapacity(unit.getCapacity());
		if(unit.getGender() != null) u.setGender(unit.getGender());
		if(unit.getUnitNumber() != null) u.setGender(unit.getGender());
		if(unit.getBuildingNumber() != null) u.setBuildingNumber(unit.getBuildingNumber());
		if(unit.getComplex() != null) {
			u.setComplex(complexService.findByComplexId(unit.getComplex().getComplexId()));
		}
		unitService.save(u);
		return u;
	}

	@DeleteMapping(value = "{id}")
	public boolean deleteUnit(@PathVariable("id") long id){
		return unitService.delete(id);
	}
	
}
