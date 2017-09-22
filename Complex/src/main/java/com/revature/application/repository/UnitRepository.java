package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

	void delete(int id);

}
