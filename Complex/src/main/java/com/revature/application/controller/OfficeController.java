package com.revature.application.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

import io.swagger.annotations.Api;

@Api(value="office")
@RestController
@RequestMapping("/office")
public class OfficeController {
	@Autowired
	OfficeService os;
	
	@GetMapping
	public Object findAll() {
		return os.findAll();
	}
	
	@GetMapping("{id}")
	public Object findOne(@PathVariable("id") int id) {
		return os.find(id);
	}
	
	@PostMapping
	public Object add(@RequestBody Office office) {
		return os.save(office);
	}
	
	@PutMapping("{id}")
	public Object update(@PathVariable("id") int id, @RequestBody Office office) {
		Office o = os.find(id);
		if(office.getAddress() != null) o.setAddress(office.getAddress());
		if(office.getPhone() != null) o.setPhone(office.getPhone());
		if(office.getTimezone() != null) o.setTimezone(office.getTimezone());
		if(office.getWebsite() != null) o.setWebsite(office.getWebsite());
		os.save(o);
		return o;
	}	
	
	@GetMapping("{id}/complexes")
	public Object findComplexes(@PathVariable("id") int id){
		Office office = os.find(id);
		if(office != null) return office.getComplexes();
		return null;
	}
	
	@DeleteMapping(value = "{id}")
	public Object delete(@PathVariable("id") int id){
		return os.delete(id);
	}
}