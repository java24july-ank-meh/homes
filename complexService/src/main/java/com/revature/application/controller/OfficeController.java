package com.revature.application.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

import io.swagger.annotations.Api;

@Api(value="office")
@RestController
@RequestMapping("office")
public class OfficeController {
	@Autowired
	OfficeService os;
	
	@GetMapping
	public List<Office> findAll() {
		return os.findAll();
	}
	
	@GetMapping("{id}")
	public Office findOne(@PathVariable("id") int id) {
		return os.find(id);
	}
	
	@PostMapping()
	public Office add() {
		//return os.save(new Office(0,street,city,state,zip,phone,website));
		return os.save(new Office(0,"street","city","state","zip","phone","website"));
	}
	
	@GetMapping("{id}/complexes")
	public Set<Complex> findComplexes(@PathVariable("id") int id){
		return os.find(id).getComplexes();
	}
}