package com.revature.application.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.model.Associate;
import com.revature.application.repository.AssociateRepository;

@Service
public class AssociateServiceImpl implements AssociateService {
	
	private AssociateRepository associateRepository;
	
	@Autowired
	public void setAssociateRepository(AssociateRepository associateRepository) {
		this.associateRepository = associateRepository;
	}

	@Override
	public List<Associate> listAll() {
		return associateRepository.findAll();
	}

	@Override
	public Associate findByAssociateId(Long id) {
		return associateRepository.findByAssociateId(id);
	}

	@Override
	public List<Associate> findByUnitId(Long id) {
		//TODO: possibly change this to actually look by Unit ID as it currently... well does not do that.
		//TODO: Get Unit model from the Unit team to get it implemented in our service to make this method actually work
		return associateRepository.findByUnitId(id);
	}

	@Override
	public Associate findByEmail(String email) {
		return associateRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		associateRepository.delete(id);
	}

	@Override
	public Associate saveOrUpdate(Associate associate) {
		return associateRepository.save(associate);
	}

}
