package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.model.Unit;
import com.revature.application.service.ComplexService;
import com.revature.application.service.OfficeService;

@RestController
@RequestMapping("complex")
public class ComplexController {
	@Autowired
	ComplexService cs;
	
	@GetMapping
	public List<Complex> findAll() {
		return cs.findAll();
	}
	
	@GetMapping("{id}")
	public Complex findOne(@PathVariable("id") int id) {
		return cs.findByComplexId(id);
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<Object> updateUnit(@PathVariable("id") int id, @RequestBody Complex complex) {
		return ResponseEntity.ok(cs.update(complex));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Object> deleteUnit(@PathVariable("id") long id){
		return ResponseEntity.ok("Unit Deleted");
	}
	
}
