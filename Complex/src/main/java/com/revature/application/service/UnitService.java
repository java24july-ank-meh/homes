package com.revature.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.model.Unit;
import com.revature.application.repository.ComplexRepository;
import com.revature.application.repository.UnitRepository;

@Service
@Transactional
public class UnitService {
	
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	ComplexRepository complexRepository;
	
	public List<Unit> findAll(){
		return unitRepository.findAll();
	}
	
	public Unit findByUnitId(long id) {
		return unitRepository.findByUnitId(id);
	}
	
	public long save(Unit unit) {
		return unitRepository.saveAndFlush(unit).getUnitId();
	}
	
	public long update(Unit unit) {
		return unitRepository.saveAndFlush(unit).getUnitId();
	}
	
	public boolean delete(long id){
		Unit u = unitRepository.findOne(id);
		u.setComplex(null);
		unitRepository.save(u);
		unitRepository.delete(id);
		return unitRepository.findOne(id) == null;
	}
}