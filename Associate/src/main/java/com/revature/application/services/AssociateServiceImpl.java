package com.revature.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
	
	private final CountDownLatch latch = new CountDownLatch(3);
	
	 @Autowired
	 private KafkaTemplate<String, String> template;
	 /*example use
	 this.template.send("myTopic", "foo1");
     this.template.send("myTopic", "foo2");
     this.template.send("myTopic", "foo3");
     latch.await(60, TimeUnit.SECONDS);
     System.out.println("Messages Sent");*/

    @KafkaListener(topics = "myTopic")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        System.out.println("################# LISTENING TO MESSAGE ###################");
        System.out.println(cr.toString());
        latch.countDown();
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
