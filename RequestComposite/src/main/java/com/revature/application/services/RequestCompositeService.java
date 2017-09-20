package com.revature.application.services;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.revature.application.models.Associate;

@Service
public class RequestCompositeService {

	
	public void sendMessage(int associateId, String message) {
		RestTemplate restTemplate = new RestTemplate();
		Associate associate = restTemplate.getForObject("http://localhost:8090/associates/"+associateId, Associate.class);
		//associate.getSlackId();
		message = "Your supplies have arrived!";
		//send slack direct message thing

	}
}
