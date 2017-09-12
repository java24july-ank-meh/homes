package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

@RestController
@RequestMapping("office")
public class ComplexController {
	@Autowired
	OfficeService os;
	
	@GetMapping
	public List<Office> findAll() {
		return os.findAll();
	}
	
	@GetMapping("{id}")
	public Office findOne(@PathVariable("id") int id) {
		System.out.println(os.findOne(id));
		return os.findOne(id);
	}
	
	@PostMapping()
	public Office add() {
		//return os.addOne(new Office(0,street,city,state,zip,phone,website));
		return os.addOne(new Office(0,"street","city","state","zip","phone","website"));
	}
}
