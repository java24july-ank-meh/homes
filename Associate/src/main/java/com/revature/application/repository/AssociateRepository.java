package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Integer> {
	public Associate findByAssociateId(int id);
	public Associate findByEmail(String email);
	public Associate findByUnitId(int id);
	public void deleteByAssociateId(int id);
}
