package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("resident")
public class ResidentController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Helper helper;
	
	/*Sends an email invite to the email and autofills first name and last name for slack registration
	does not enforce names*/
	@PostMapping("invite")
	public  ResponseEntity<Object> invite(String token, String email, String firstName, String lastName){
	String requestUrl = "https://slack.com/api/users.admin.invite?token=" +
			token +"&email=" + email +
	"&first_name=" + firstName + "&last_name=" + lastName;

	String responseString = restTemplate.getForObject(requestUrl, String.class);
	
	return ResponseEntity.ok(responseString);
	}

	@PostMapping("message/{complex}/{unit}")
	public ResponseEntity<Object> messageChannel( @PathVariable("token") String token, @PathVariable("channelName") String channelName, 
			@PathVariable("message") String message){
		
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/chat.postMessage?token=" + token +
				 "&channel="+ channelId+"&text=" + message;
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
	/*sends a message and @mentions all users in the userIds List*/
	@PostMapping("message/users")
	public ResponseEntity<Object> messageUser(@PathVariable("token") String token, @PathVariable("channelName") String channelName, 
			@PathVariable("message") String message, List<String> userIds){
		
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/chat.postMessage?token=" + token +
				 "&channel="+ channelId+"&text=" + message;
		for(int i = 0; i<userIds.size(); i++) {
			requestUrl += " <@" + userIds.get(i) + ">";
		}
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
	/*send list of [userid1,username1, uid2,un2,...] to front end*/
	@RequestMapping("message/listUsers")
	public ResponseEntity<Object> listUser(@PathVariable("token") String token, @PathVariable("channelName") String channelName){
		
		List<String> userArray = helper.getAllUsersInChannel(channelName);
		
		return ResponseEntity.ok(userArray);
		
	}
	
	@PostMapping("message/users/direct")
	public ResponseEntity<Object> messageUserDirect(@PathVariable("token") String token, @PathVariable("channelName") String channelName, 
			@PathVariable("message") String message, @PathVariable("userIds") List<String> userIds, @PathVariable("group") int group){
		
		String channelId = helper.getChannelId(channelName);
		String responseString ="";
		
		//individual message/s
		if(group == 1) {
			responseString = helper.multiMessage(token, channelId, message, userIds);
		}
		//group direct message
		else {
			responseString = helper.directMessage(token, channelId, message, userIds);
		}

		return ResponseEntity.ok(responseString);
		
	}
	
	/*kicks a user from an apartment channel*/
	@PostMapping("kick/users")
	public ResponseEntity<Object> kickUser(@PathVariable("token") String token, @PathVariable("channelName") String channelName, 
			@PathVariable("userId") String userId){
		
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/channels.kick?token=" + token +
				 "&channel="+ channelId+"&user=" + userId;

		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
}
