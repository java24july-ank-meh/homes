package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.models.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Integer> {
	

	

}
