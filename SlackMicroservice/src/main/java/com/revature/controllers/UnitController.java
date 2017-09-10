package com.revature.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@Api(value="unit", description="This service creates, updates, and deletes slack channels for individual units")
@RestController
@RequestMapping("unit")
public class UnitController {

	@PostMapping("create/{unit}")
	public String createUnit(@PathVariable("unit") String unit) {
		return null;
	}
	
	@PostMapping("update/{unit}")
	public String updateUnit(@PathVariable("unit") String unit) {
		return null;
	}
	
	@PostMapping("delete/{unit}")
	public String deleteUnit(@PathVariable("unit") String unit) {
		return null;
	}
}
