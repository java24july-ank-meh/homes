package com.revature.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wordnik.swagger.annotations.Api;

import net.minidev.json.parser.JSONParser;

@Api(value="unit", description="This service creates, updates, and deletes slack channels for apartment complexes")
@Service
@RestController
@RequestMapping("complex")
public class ComplexController {

	/*restTemplate bean handles all http requests to slack API. A single RestTemplate
	 * object can be used for multiple requests.
	 */
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Helper helper;
	
	@HystrixCommand(fallbackMethod="createFallback")
	@PostMapping("create")
	public ResponseEntity<String> createComplex(@RequestBody String complex) {
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name.*/
		
		JSONObject json = null;
		String name = null;
		String token = helper.getToken();
		try{
			json = new JSONObject(complex);
			name = json.getString("name");
			token = json.getString("token");
		}
		catch(JSONException e) {e.printStackTrace();}
		
		String channelName = helper.complexChannelName(name);
		
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
	public ResponseEntity<String> updateComplex(@RequestBody String complex, 
			HttpSession http) {
		
		String token =  helper.getToken();
		
		System.out.println(complex);
		JSONObject json = null;
		String oldChannelName = null; String newComplexName = null;
		try{
			json = new JSONObject(complex);
			oldChannelName = json.getString("oldName");
			newComplexName = json.getString("newName");
			token = json.getString("token");
		}
		catch(JSONException e) {e.printStackTrace();}
		
		String newChannelName = helper.complexChannelName(newComplexName);
		
		//needs token, channel, name
		String url = "https://slack.com/api/channels.rename";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		params.add("token", token);
		params.add("channel", helper.getChannelId(oldChannelName, token));
		params.add("name", newChannelName);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		ResponseEntity<String> response = 
				restTemplate.postForEntity(url, request, String.class);
		return response; 
	}
	
	@PostMapping("delete")
	public ResponseEntity<String> deleteComplex(@RequestBody String complex, 
			HttpSession http) {
		
		JSONObject json = null;
		String channelName = null;
		String token = helper.getToken();
		try {
			json = new JSONObject(complex);
			channelName = json.getString("channelName");
			token = json.getString("token");
		}catch(JSONException e) {
			e.printStackTrace();
		}

		
		String channelId = helper.getChannelId(channelName, token);
		
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
	
	@GetMapping("channelName/{complex}")
	public ResponseEntity<Object> channelName(@PathVariable("complex") String complex){
		
		String channelName = helper.complexChannelName(complex);
		
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