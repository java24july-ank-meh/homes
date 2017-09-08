package com.revature.apartment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.apartment.model.Complex;

public interface ComplexRepository extends JpaRepository<Complex, Integer> {
	public Complex findBycomplexId(int id);
	
}
