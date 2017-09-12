<<<<<<< HEAD
package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

@RestController
@RequestMapping("/office")
public class OfficeController {
	@Autowired
	OfficeService os;
	
	@GetMapping
	public List<Office> findAll() {
		return os.findAll();
	}
	
	@GetMapping("{id}")
	public Office findOne(@PathVariable("id") int id) {
		return os.findOne(id);
	}
}
=======
package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

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
}
>>>>>>> f4f780e488622afc022d338905120ecf6c28909c
