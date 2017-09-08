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

	public List<Supply> findByUnitId(int unitId) {
		return supplyRepository.findByUnitId(unitId);
	}

	public Object save(Supply supply) {
		
		return supplyRepository.saveAndFlush(supply).getSuppliesId();
	}

	public Supply findById(int supplyId) {
		return supplyRepository.findBySupplyId(supplyId);
	}

	public Object update(Supply supply) {
		return supplyRepository.save(supply).getSuppliesId();
	}
}
