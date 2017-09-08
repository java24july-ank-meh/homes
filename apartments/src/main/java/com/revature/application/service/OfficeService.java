package com.revature.application.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.application.model.Office;
import com.revature.application.repository.OfficeRepo;

@Service
@Transactional
public class OfficeService {
	@Autowired
	OfficeRepo or;
	
	public List<Office> findAll() {
		return or.findAll();
	}
	
	public Office findOne(int id) {
		return or.findOne(id);
	}
}
