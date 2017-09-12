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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Helper {
	
	public static Map<String, String> slackProps;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
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

	/* Returns id assigned to slack channel with given name
	 * Use for methods that require slack channel id (e.g. editing channel name)
	 */
	public String getChannelId(String name) {
	
		JsonNode channelList = getChannelList();
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
	
	public boolean channelNameIsUnique(String name) {
		JsonNode channelList = getChannelList();
		Iterator<JsonNode> channels = channelList.elements();
		
		while(channels.hasNext()) {
			if(name.equals(channels.next().path("name").asText())){return false;}
		}
		
		return true;
	}
	
	public JsonNode getChannelList() {
		
		String url = "https://slack.com/api/channels.list?token="+
				slackProps.get("client_token");
	
		String responseString = restTemplate.getForObject(url, String.class);
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
	/* Returns a slack channel name for a given apartment complex
	 * (keeping in mind that all slack channel names must be less than 21 characters)
	 * orrrrr... just have manager choose a unique abbreviation (<10 chars)????
	 */
	public static String complexChannelName(String complex) {
		return null; 
	}
	
	// Returns a slack channel name for a given apartment unit
	public static String unitChannelName(String complex, String unit) {
		return null;
	}
}
