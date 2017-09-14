package com.revature.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.wordnik.swagger.annotations.Api;

@Api(value="unit", description="This service allows users to message other users or their channel over slack")
@RestController
@RequestMapping("resident")
public class ResidentController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Helper helper ;
	
	
	/*Sends an email invite to the email and autofills first name and last name for slack registration
	does not enforce names*/
	@PostMapping("invite")
	public  ResponseEntity<Object> invite(@RequestPart("email") String email, @RequestPart("fname") String firstName, @RequestPart("lname") String lastName){
		
	String token = Helper.slackProps.get("client_token");
	String requestUrl = "https://slack.com/api/users.admin.invite?token=" +
			token +"&email=" + email +
	"&first_name=" + firstName + "&last_name=" + lastName;

	String responseString = restTemplate.getForObject(requestUrl, String.class);
	
	return ResponseEntity.ok(responseString);
	}

	@PostMapping("message/{complex}/{unit}")
	public ResponseEntity<Object> messageChannel(@PathVariable("complex") String complex, @PathVariable("unit") String unit, 
			@RequestPart("message") String message){
		
		String token = Helper.slackProps.get("client_token");
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/chat.postMessage?token=" + token +
				 "&channel="+ channelId+"&text=" + message;
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
	/*sends a message and @mentions all users in the userIds List*/
	@PostMapping("message/users/{complex}/{unit}")
	public ResponseEntity<Object> messageUser(@PathVariable("complex") String complex, @PathVariable("unit") String unit, 
			@RequestPart("message") String message, @RequestBody List<String> userIds){
		
		String token = Helper.slackProps.get("client_token");
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/chat.postMessage?token=" + token +
				 "&channel="+ channelId+"&text=" + message;
		for(int i = 0; i<userIds.size(); i++) {
			requestUrl += " <@" + userIds.get(i) + ">";
		}
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
	@PostMapping("message/users/direct/{complex}/{unit}")
	public ResponseEntity<Object> messageUserDirect(@PathVariable("complex") String complex, @PathVariable("unit") String unit, 
			/*@RequestPart("message") String message, @RequestPart("group") String group,  @RequestBody String userIds2,*/HttpServletRequest req){
		
		String message = req.getParameter("message");
		String group = req.getParameter("group");
		String ids = req.getParameter("ids");
		
		List<String>userIds = new ArrayList<String>(Arrays.asList(ids.split(",")));
		System.out.println(userIds);
		
		String token = Helper.slackProps.get("client_token");
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		String responseString ="";
		
		//individual message/s
		if(group.equals("0")) {
			System.out.println("not group");
			responseString = helper.multiMessage(token, channelId, message, userIds);
		}
		//group direct message
		else {
			responseString = helper.directMessage(token, channelId, message, userIds);
		}

		return ResponseEntity.ok(responseString);
		
	}
	
	/*kicks a user from an apartment channel*/
	@PostMapping("kick/users/{complex}/{unit}")
	public ResponseEntity<Object> kickUser(@PathVariable("complex") String complex, 
			@PathVariable("unit") String unit, @RequestBody String userId){
		
		String token = Helper.slackProps.get("client_token");
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		
		String requestUrl  = "https://slack.com/api/channels.kick?token=" + token +
				 "&channel="+ channelId+"&user=" + userId;
		
		String responseString = restTemplate.getForObject(requestUrl, String.class);
		return ResponseEntity.ok(responseString);
		
	}
	
	/*send list of [userid1,username1, uid2,un2,...] to front end*/
	@RequestMapping(value="message/listUsers/{complex}/{unit}", method=RequestMethod.GET)
	public ResponseEntity<Object> listUser(@PathVariable("complex") String complex, @PathVariable("unit") String unit){
		
		String channelName = complex + unit;
		List<String> userList = helper.getAllUsersInChannel(channelName);
		System.out.println(userList);
		
		return ResponseEntity.ok(userList);
		
	}
	
}
