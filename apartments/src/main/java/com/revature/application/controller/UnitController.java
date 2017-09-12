package com.revature.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.model.Unit;
import com.revature.application.service.UnitService;

@RestController
public class UnitController {

	@Autowired
	UnitService unitService;

	@RequestMapping(value = "/Unit", method = RequestMethod.GET)
	public ResponseEntity<Object> displayAllUnit() {
		return ResponseEntity.ok(unitService.findAll());
	}

	/*@RequestMapping(value = "/Complex/{id}/Unit", method = RequestMethod.GET)
	public ResponseEntity<Object> displayUnitFromComplex(@PathVariable("id") long id) {
		return ResponseEntity.ok(unitService.findByUnitId(id));
	}*/

	@RequestMapping(value = "/Unit/{id}")
	public ResponseEntity<Object> displayUnit(@PathVariable("id") long id) {
		return ResponseEntity.ok(unitService.findByUnitId(id));
	}
	
	/*@RequestMapping(value ="Complexes/{id}/Apartments/create", method=RequestMethod.POST)
	public ResponseEntity<Object> createUnit(@PathVariable("id") int id, @RequestBody Unit unit){
		return ResponseEntity.ok(unitService.save(unit));
	}*/

	@RequestMapping(value = "/Unit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateUnit(@PathVariable("id") long id, @RequestBody Unit unit) {
		return ResponseEntity.ok(unitService.update(unit));
	}

	@DeleteMapping(value = "Unit/{id}")
	public ResponseEntity<Object> deleteUnit(@PathVariable("id") long id){
		return ResponseEntity.ok("Unit Deleted");
	}
	
}
