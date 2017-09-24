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

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	/*restTemplate bean handles all http requests to slack API. A single RestTemplate
	 * object can be used for multiple requests.
	 */
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Helper helper;
	
	@HystrixCommand(fallbackMethod="createFallback")
	@PostMapping("create")
	public ResponseEntity<String> createUnit(@RequestBody String body, 
			HttpSession http) {
		
		JSONObject json = null;
		String complex = null; String unit = null;
		String token = null;
		try {
			json = new JSONObject(body);
			token = json.getString("token");
			complex = json.getString("complex");
			unit = json.getString("unit");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name.*/
		String channelName = complex + unit;
		String url = null;
		String channelId = null;
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		if(helper.channelNameIsUnique(channelName, token)) {
			//needs token and channelName 
			url = "https://slack.com/api/channels.create";
			params.add("name", channelName);
		}
		else {
			channelId = helper.getChannelId(channelName, token);
			//needs token and channelId
			url = "https://slack.com/api/channels.unarchive";
			params.add("channel", channelId);
		}
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
	 
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
	
		channelId = helper.getChannelId(channelName, token);
		System.out.println("new channel id: " + channelId);
		
		return response;
	}
	
	@PostMapping("update")
	public ResponseEntity<String> updateUnit(@RequestBody String body, 
			HttpSession http) {
		
		JSONObject json = null;
		String complex = null; String unit = null; 
		String newComplex = null; String newUnit = null;
		String token = null;
		try {
			json = new JSONObject(body);
			token = json.getString("token");
			complex = json.getString("complex");
			unit = json.getString("unit");
			newComplex = json.getString("newComplex");
			newUnit = json.getString("newUnit");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		String oldName = complex + unit;
		String newName = newComplex + newUnit;
		
		//needs token, channel, name
		String url = "https://slack.com/api/channels.rename";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		params.add("channel", helper.getChannelId(oldName, token));
		params.add("name", newName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		ResponseEntity<String> response = 
				restTemplate.postForEntity(url, request, String.class);
		return response;
	}
	
	@PostMapping("delete")
	public ResponseEntity<String> deleteUnit(@RequestBody String body,
			HttpSession http) {
		
		JSONObject json = null;
		String complex = null; String unit = null; String token = null;
		try {
			json = new JSONObject(body);
			token = json.getString("token");
			complex = json.getString("complex");
			unit = json.getString("unit");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName, token);
		
		System.out.println("name: " + channelName + ", channelId: " + channelId);
		
		//needs token, channel
		String url = "https://slack.com/api/channels.archive";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		params.add("channel", channelId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
	 
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		
		return response;
	}
	
	public void createFallback() {
		System.out.println("Reached create fallback");
	}
}
