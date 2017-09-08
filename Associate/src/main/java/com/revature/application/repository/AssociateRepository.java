package com.revature.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
	public Associate findByAssociateId(Long id);
	public Associate findByEmail(String email);
	public List<Associate> findByUnitId(Long id); //Because there might be multiple people in one unit
	public void deleteByAssociateId(Long id);
}
