package com.revature.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.model.Unit;
import com.revature.application.repository.UnitRepository;

@Service
@Transactional
public class UnitService {
	
	@Autowired
	UnitRepository unitRepository;
	
	
	public List<Unit> findAll(){
		return unitRepository.findAll();
	}
	
	/*public List<Unit> findByComplexId(Long id) {
		Complex apartmentComplex = ComplexRepository.findByComplexId(id);
		return apartmentComplex.getUnit();*/
	
	public Unit findByUnitId(long id) {
		return unitRepository.findByUnitId(id);
	}
	
	public long save(Unit unit) {
		return unitRepository.saveAndFlush(unit).getUnitId();
	}
	
	public long update(Unit unit) {
		return unitRepository.saveAndFlush(unit).getUnitId();
	}
	
	public void delete(Unit unit){
		unitRepository.delete(unit);
	}
}