package com.revature.application.services;

import java.util.List;

import com.revature.application.model.Associate;

public interface AssociateService {
	
	public List<Associate> listAll();
	
	public Associate findByAssociateId(Long id);
	
	public Associate findByUnitId(Long id);
	
	public Associate findByEmail(String email);
	
	public void delete(Long id);
	
	public Associate saveOrUpdate(Associate associate);

	
}
