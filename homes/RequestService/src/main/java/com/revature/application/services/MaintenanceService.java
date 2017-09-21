package com.revature.application.services;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
	
	 @Autowired
	 KafkaTemplate<String, String> template;
	
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
	

	public void run(String... args) throws Exception
	{
		// this.template.send("myTopic", "foo1");
		// this.template.send("myTopic", "foo2");
		// this.template.send("myTopic", "foo3");
		// latch.await(60, TimeUnit.SECONDS);
		// System.out.println("Messages Sent");
	}

	@KafkaListener(topics = "request")
	public void listen(ConsumerRecord<?, ?> cr) throws Exception
	{
		// System.out.println("################# LISTENING TO MESSAGE
		// ###################");
		// System.out.println(cr.toString());
		// latch.countDown();
	}
}