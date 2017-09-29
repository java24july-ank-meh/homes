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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
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
		String complex = null; String unit = null; String building = null;
		String token = helper.getToken();
		try {
			json = new JSONObject(body);
			complex = json.getString("complex");
			building = json.getString("building");
			unit = json.getString("unit");
			token = json.getString("token");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name.*/
		String channelName = helper.unitChannelName(complex, building, unit);
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
		String oldName = null;
		String newComplex = null; String newBuilding = null; String newUnit = null;
		String token = helper.getToken();
		try {
			json = new JSONObject(body);
			oldName = json.getString("oldName");
			newComplex = json.getString("newComplex");
			newBuilding = json.getString("newBuilding");
			newUnit = json.getString("newUnit");
			token = json.getString("token");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		String newName = helper.unitChannelName(newComplex, newBuilding, newUnit);
		
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
		String channelName = null; 
		String token = helper.getToken();
		try {
			json = new JSONObject(body);
			channelName = json.getString("channelName");
			token = json.getString("token");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
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
	
	@GetMapping("channelName/{complex}/{building}/{unit}")
	public ResponseEntity<Object> channelName(@PathVariable("complex") String complex, 
			@PathVariable("building") String building, @PathVariable("unit") String unit){
		
		String channelName = helper.unitChannelName(complex, building, unit);
		
		List<JSONObject> result = new ArrayList<>();
		JSONObject json = new JSONObject();
		try{json.put("channelName", channelName);}
		catch(JSONException e) {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		result.add(json);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	public void createFallback() {
		System.out.println("Reached create fallback");
	}
}
