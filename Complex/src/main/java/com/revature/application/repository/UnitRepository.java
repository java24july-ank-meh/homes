package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

//	void delete(int id);
//	Unit findByUnitId(int id);

}
