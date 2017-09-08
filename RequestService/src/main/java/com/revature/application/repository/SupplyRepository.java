package com.revature.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.models.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Integer> {

	List<Supply> findByUnitId(int supplyId);

	Supply findBySupplyId(int supplyId);
	

	

}
