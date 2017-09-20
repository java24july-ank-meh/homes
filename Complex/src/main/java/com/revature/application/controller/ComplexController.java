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
	
	@Autowired
	OfficeService os;
	
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
		if(complex.getOffice() != null) {
			complex.setOffice(os.find(complex.getOffice().getOfficeId()));
		}
		return cs.save(complex);
	}
	
	@PutMapping(value = "{id}")
	public Object updateComplex(@PathVariable("id") int id, @RequestBody Complex complex) {
		Complex com = cs.findByComplexId(id);
		if(complex.getAddress() != null) com.setAddress(complex.getAddress());
		if(complex.getParking() != null) com.setParking(complex.getParking());
		if(complex.getWebsite() != null) com.setWebsite(complex.getWebsite());
		if(complex.getEmail() != null) com.setEmail(complex.getEmail());
		if(complex.getPhone() != null) com.setPhone(complex.getPhone());
		if(complex.getName() != null) com.setName(complex.getName());
		if(complex.getAbbreviation() != null) com.setAbbreviation(complex.getAbbreviation());
		if(complex.getPhotoUrl() != null) com.setPhotoUrl(complex.getPhotoUrl());
		if(complex.getOffice() != null) {
			com.setOffice(os.find(complex.getOffice().getOfficeId()));
		}
		return cs.save(com);
	}

	@DeleteMapping(value = "{id}")
	public Object deleteComplex(@PathVariable("id") int id){
		 return cs.delete(id);
		 
	}
	
}
