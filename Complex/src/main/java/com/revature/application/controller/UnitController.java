package com.revature.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Unit;
import com.revature.application.service.UnitService;

@RestController
@RequestMapping("unit")
public class UnitController {

	@Autowired
	UnitService unitService;

	@GetMapping
	public Object displayAllUnit() {
		return unitService.findAll();
	}

	@GetMapping(value = "{id}")
	public Object displayUnit(@PathVariable("id") long id) {
		return unitService.findByUnitId(id);
	}
	
	@PostMapping
	public Object createUnit(@RequestBody Unit unit) {
		return unitService.save(unit);
	}
	
	@PutMapping(value = "{id}")
	public Object updateUnit(@PathVariable("id") long id, @RequestBody Unit unit) {
		Unit u = unitService.findByUnitId(id);
		if(unit.getCapacity() != 0) u.setCapacity(unit.getCapacity());
		if(unit.getComplex() != null) u.setComplex(unit.getComplex());
		if(unit.getGender() != null) u.setGender(unit.getGender());
		if(unit.getUnitNumber() != null) u.setGender(unit.getGender());
		unitService.save(u);
		return u;
	}

	@DeleteMapping(value = "{id}")
	public boolean deleteUnit(@PathVariable("id") long id){
		return unitService.delete(id);
	}
	
}
