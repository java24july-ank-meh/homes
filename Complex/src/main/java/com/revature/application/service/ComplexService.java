package com.revature.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.model.Complex;
import com.revature.application.repository.ComplexRepository;


@Service
@Transactional
public class ComplexService {
	@Autowired
	ComplexRepository ComplexRepository;

	public List<Complex> findAll() {
		return ComplexRepository.findAll();
	}
	
	public Complex findByComplexId(int id) {
		return ComplexRepository.findOne(id);
	}
	

	public int save(Complex complex){
		return ComplexRepository.saveAndFlush(complex).getComplexId();
	}
	
	public boolean delete(int id) {
		ComplexRepository.delete(id);
		return true;
	}

}

