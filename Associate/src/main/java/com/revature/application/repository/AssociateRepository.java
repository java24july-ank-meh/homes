package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
	public Associate findByAssociateId(Long id);
	public Associate findByEmail(String email);
	public Associate findByUnitId(Long id);
	public void deleteByAssociateId(Long id);
}
