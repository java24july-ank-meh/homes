package com.revature.application.service;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.revature.application.controller.ProfileCompositeController.service_url;

@Service
public class ProfileCompositeService {
	
    
	public String getProfile() {
						
		return null;
	}
	
	private <T> Object getObject(String endpoint1, String endpoint2, Class<T> objectClass) {
		Object obj = null;
		RestTemplate restTemplate = new RestTemplate();

		if(endpoint2.isEmpty()) {
			obj = restTemplate.getForEntity(getRestServiceURI()+"/"+endpoint1, objectClass).getBody();
		}else {
			obj = restTemplate.getForEntity(getRestServiceURI()+"/"+endpoint1+"/"+endpoint2, objectClass).getBody();
		}

		return obj;
	}

	private static URI getRestServiceURI() {
		String loc = service_url;
		String site = "/api";//idk if this is right..?
		return UriBuilder.fromUri(loc+site).build();
	}
	
}
