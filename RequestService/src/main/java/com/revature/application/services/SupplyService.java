package com.revature.application.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.application.models.Supply;
import com.revature.application.repository.SupplyRepository;

@Service
@Transactional
public class SupplyService {

	@Autowired
	SupplyRepository supplyRepository;
	
	public List<Supply> findAll(){
		return supplyRepository.findAll();
	}
}
