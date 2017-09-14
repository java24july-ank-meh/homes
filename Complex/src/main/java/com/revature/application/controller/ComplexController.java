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
	public Object findAll() {
		return cs.findAll();
	}
	
	@GetMapping("{id}")
	public Object findOne(@PathVariable("id") int id) {
		return cs.findByComplexId(id);
	}
	
	@GetMapping("{id}/units")
	public Object findUnits(@PathVariable("id") int id) {
		return cs.findByComplexId(id).getUnits();
	}
	
	@PostMapping
	public Object createComplex(@RequestBody Complex complex) {
		return cs.save(complex);
	}
	
	@PutMapping(value = "{id}")
	public Object updateComplex(@PathVariable("id") int id, @RequestBody Complex complex) {
		Complex com = cs.findByComplexId(id);
		if(complex.getCity() != null) com.setCity(complex.getCity());
		if(complex.getStreet() != null) com.setCity(complex.getStreet());
		if(complex.getState	() != null) com.setCity(complex.getState());
		if(complex.getZip() != null) com.setCity(complex.getZip());
		if(complex.getParking() != null) com.setCity(complex.getParking());
		if(complex.getWebsite() != null) com.setCity(complex.getWebsite());
		if(complex.getEmail() != null) com.setCity(complex.getEmail());
		if(complex.getPhone() != null) com.setCity(complex.getPhone());
		if(complex.getName() != null) com.setCity(complex.getName());
		if(complex.getAbbreviation() != null) com.setCity(complex.getAbbreviation());
		cs.save(com);
		return cs.update(complex);
	}

	@DeleteMapping(value = "{id}")
	public Object deleteComplex(@PathVariable("id") int id){
		 return cs.delete(id);
		 
	}
	
}
