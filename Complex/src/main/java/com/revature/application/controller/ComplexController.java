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
@RequestMapping("/complex")
public class ComplexController {
	@Autowired
	ComplexService cs;
	
	@GetMapping
	public ResponseEntity<List<Complex>> findAll() {
		return ResponseEntity.ok(cs.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> findOne(@PathVariable("id") int id) {
		return ResponseEntity.ok(cs.findByComplexId(id));
	}
	@PostMapping
	public ResponseEntity<Object> createComplex(@RequestBody Complex complex) {
		return ResponseEntity.ok(cs.save(complex));
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<Object> updateComplex(@PathVariable("id") int id, @RequestBody Complex complex) {
		return ResponseEntity.ok(cs.update(complex));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<String> deleteComplex(@PathVariable("id") int id){
		 return ResponseEntity.ok(cs.delete(id));
		 
	}
	
}
