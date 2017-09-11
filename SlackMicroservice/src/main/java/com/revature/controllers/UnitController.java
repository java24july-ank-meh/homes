package com.revature.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wordnik.swagger.annotations.Api;

@Api(value="unit", description="This service creates, updates, and deletes slack channels for individual units")
@Service
@RestController
@RequestMapping("unit")
public class UnitController {
	
	static Map<String, String> slackProps;
	
	/*The following static block loads all properties necessary for accessing the slack 
	 *api (mostly, the client token). */
	static {
		slackProps = new HashMap<>();
		InputStream file = null;
		Properties props = new Properties();
		
		try {
			file = new FileInputStream("src/main/java/slack.properties");
			props.load(file);
			Set<String> propNames = props.stringPropertyNames();
			for(String name : propNames) {
				slackProps.put(name, props.getProperty(name));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*restTemplate bean handles all http requests to slack API. A single RestTemplate
	 * object can be used for multiple requests.
	 */
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="createFallback")
	@PostMapping("create/{complex}/{unit}")
	public ResponseEntity<Object> createUnit(@PathVariable("complex") String complex, 
			@PathVariable("unit") String unit) {
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name. This should 
		 *probably be changed to send these parameters in an HTTP POST body instead of 
		 *sending them as URL parameters.*/
		String url = "https://slack.com/api/channels.create?token="+
				slackProps.get("client_token") + "&name=" + complex + unit;
		
		String responseString = restTemplate.getForObject(url, String.class);
		
		List<String> response = new ArrayList<>();
		response.add(responseString);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("update/{unit}")
	public String updateUnit(@PathVariable("unit") String unit) {
		return null;
	}
	
	@PostMapping("delete/{unit}")
	public String deleteUnit(@PathVariable("unit") String unit) {
		return null;
	}
	
	public void createFallback() {
		System.out.println("Reached create fallback");
	}
}
