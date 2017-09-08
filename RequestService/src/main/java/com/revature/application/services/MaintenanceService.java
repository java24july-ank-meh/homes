package com.revature.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.models.Maintenance;
import com.revature.application.repository.MaintenanceRepository;



@Service
@Transactional
public class MaintenanceService
{
	@Autowired
	MaintenanceRepository maintenanceRepository;
	
	public List<Maintenance> findAll(){
		return maintenanceRepository.findAll();
	}
	
	public List<Maintenance> findByUnitId(int unitId){
		return maintenanceRepository.findByUnitId(unitId);
	}
	
	public Maintenance findById(int maintenanceId){
		return maintenanceRepository.findByMaintenanceId(maintenanceId);
	}
	
	public int save(Maintenance maintenance){
		return maintenanceRepository.saveAndFlush(maintenance).getMaintenanceId();
	}
	
	public int update(Maintenance maintenance){
		return maintenanceRepository.save(maintenance).getMaintenanceId();
	}
}