package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
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
	
	@PostMapping()
	public int add() {
		return cs.save(new Complex(0,"website","email","phone","name","abbreviation","street","city","state","zip","parking",null));
	}
}
