package com.revature.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;

	/* Returns id assigned to slack channel with given name
	 * Use for methods that require slack channel id (e.g. editing channel name)
	 */
	public String getChannelId(String name, String userToken) {
	
		JsonNode channelList = getChannelList(userToken);
		Iterator<JsonNode> channels = channelList.elements();
			
		while(channels.hasNext()) {
			JsonNode nextChannel = channels.next();
			JsonNode nextChannelName = nextChannel.path("name");
			if(name.equals(nextChannelName.asText())) {
				return nextChannel.path("id").asText();
			}
		}
		
		return null;
	}
	
	public boolean channelNameIsUnique(String name, String userToken) {
		JsonNode channelList = getChannelList(userToken);
		Iterator<JsonNode> channels = channelList.elements();
		
		while(channels.hasNext()) {
			if(name.equals(channels.next().path("name").asText())){return false;}
		}
		
		return true;
	}
	
	public JsonNode getChannelList(String userToken) {
		
		String url = "https://slack.com/api/channels.list";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		params.add("token", userToken);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		String responseString = 
				restTemplate.postForObject(url, request, String.class);
	
		//String responseString = restTemplate.getForObject(url, String.class);
		JsonNode root, channelElement = null;
		Iterator<JsonNode> channels = null;
		
		try {
			root = objectMapper.readTree(responseString);
			channelElement = root.path("channels");
			return channelElement;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//Extracts name parameter from json  
	public String nameParameter(String json) {
		String channelName = json.split(":")[1];
		channelName = channelName.substring(1, channelName.length()-2);
		return channelName;
	}
}
