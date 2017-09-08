package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Complex;

public interface ComplexRepository extends JpaRepository<Complex, Integer> {
	public Complex findBycomplexId(int id);
	
}
