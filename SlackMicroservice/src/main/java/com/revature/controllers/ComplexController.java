package com.revature.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
	public ResponseEntity<String> createComplex(@RequestBody String complex, HttpSession http) {
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name.*/
		
		JSONObject json = null;
		String channelName = null;
		try{
			json = new JSONObject(complex);
			channelName = json.getString("name"); System.out.println(channelName);
		}
		catch(JSONException e) {e.printStackTrace();}
		
		SecurityContext sc = (SecurityContextImpl) http.getAttribute("SPRING_SECURITY_CONTEXT");
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) sc.getAuthentication().getDetails();
		String token =  details.getTokenValue();
		
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
		
		SecurityContext sc = (SecurityContextImpl) http.getAttribute("SPRING_SECURITY_CONTEXT");
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) sc.getAuthentication().getDetails();
		String token =  details.getTokenValue();
		
		System.out.println(complex);
		JSONObject json = null;
		String oldName = null; String newName = null;
		try{
			json = new JSONObject(complex);
			oldName = json.getString("oldName"); System.out.println(oldName);
			newName = json.getString("newName"); System.out.println(newName);
		}
		catch(JSONException e) {e.printStackTrace();}
		
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
	public ResponseEntity<String> deleteComplex(@RequestBody String complex, 
			HttpSession http) {
		
		JSONObject json = null;
		String name = null;
		try {
			json = new JSONObject(complex);
			name = json.getString("name");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		SecurityContext sc = (SecurityContextImpl) http.getAttribute("SPRING_SECURITY_CONTEXT");
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) sc.getAuthentication().getDetails();
		String token =  details.getTokenValue();
		
		String channelId = helper.getChannelId(name, token);
		
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
