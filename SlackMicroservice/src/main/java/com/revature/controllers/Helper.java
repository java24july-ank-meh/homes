package com.revature.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
			System.out.println(" \n\n\n\n RUNNING STATIC BLOCK \n\n\n\n");
			//file = new FileInputStream("src/main/java/slack.properties");
			// \/\/ shitty madrone version \/\/
			file = new FileInputStream("C:\\docs\\Revature\\batch-src\\homes\\SlackMicroservice\\src\\main\\java\\slack.properties");
			// /\/\ shitty madrone version /\/\
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
	
	/*	Returns ArrayList with the slack ID's of all users in a given channel*/
	
	public List<String> getAllUsersInChannel(String channelName) {
		
		String channelId = getChannelId(channelName);
		
		
		String requestUrl = "https://slack.com/api/channels.info?token=" +slackProps.get("client_token") +
				 "&channel="+ channelId;
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		JsonNode rootNode, channelNode, memNode = null;
		List<String> users = new ArrayList<String>();
		
		try {
			rootNode = objectMapper.readTree(responseString);
			channelNode = rootNode.path("channel");
			memNode = channelNode.path("members");
			
			Iterator<JsonNode> elements = memNode.elements();
			while(elements.hasNext()){
				JsonNode member = elements.next();
				users.add(member.asText());
				users.add(getUserName(member.asText()) );
			}
			System.out.println(users);
			return users;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*	Returns string with UserName given slack users ID*/
	public String getUserName(String userId) {
		
		String requestUrl = "https://slack.com/api/users.info?token=" +slackProps.get("client_token") +
				 "&user="+ userId;
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		JsonNode rootNode, userNode, idNode = null;
		String userName = "";
		
		try {
			rootNode = objectMapper.readTree(responseString);
			userNode = rootNode.path("user");
			idNode = userNode.path("name");
			userName = idNode.asText();
			
			return userName;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String  startConvo(List<String> userIds, String channelId) {
		
		String requestUrl = "https://slack.com/api/conversations.open?token=" +slackProps.get("client_token") +
				 "&return_im=false" + "&users=";
		
		for(int i = 0; i<(userIds.size()-1); i++) {
			requestUrl += userIds.get(i) + "%2C";
		}
		requestUrl += userIds.get(userIds.size()-1);
		requestUrl +="&pretty=1";
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		JsonNode rootNode, chanNode, idNode = null;
		String channel = "";
		
		try {
			rootNode = objectMapper.readTree(responseString);
			chanNode = rootNode.path("channel");
			idNode = chanNode.path("id");
			channel = idNode.asText();
			
			System.out.println("open channel: "+ requestUrl);
			System.out.println(responseString);
			System.out.println("channel: "+channel);
			return channel;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String  directMessage(String token, String channelId, String message, List<String> userIds) {
		
		String newChannel = startConvo(userIds, channelId);
		
		String requestUrl = "https://slack.com/api/chat.postMessage?token=" +slackProps.get("client_token") +
				"&channel="+newChannel + "&return_im=false" + "&text="+message;
			
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		System.out.println("direct message: "+message+" " +requestUrl);
		return responseString;
	}
	
	public String  multiMessage(String token, String channelId, String message, List<String> userIds) {
		
		List<String> channels = new ArrayList<String>();
		String responseString = "";
		
		for(int i = 0; i < userIds.size(); i++) {
			channels.add(startConvo(userIds, channelId) );
			List<String> singleUser = new ArrayList<String>();
			singleUser.add(userIds.get(i));
			
			responseString = directMessage(token, channelId, message, singleUser);
		}
				
		return responseString;
	}
	
}
