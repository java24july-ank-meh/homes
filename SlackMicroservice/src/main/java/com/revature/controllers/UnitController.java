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

import javax.websocket.server.PathParam;

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
	
	/*restTemplate bean handles all http requests to slack API. A single RestTemplate
	 * object can be used for multiple requests.
	 */
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Helper helper;
	
	@HystrixCommand(fallbackMethod="createFallback")
	@PostMapping("create/{complex}/{unit}")
	public String createUnit(@PathVariable("complex") String complex, 
			@PathVariable("unit") String unit) {
		
		/*The url string includes the endpoint and all necessary parameters. For slack's 
		 *channel.create method, we need the app token and complex name. This should 
		 *probably be changed to send these parameters in an HTTP POST body instead of 
		 *sending them as URL parameters.*/
		String channelName = complex + unit;
		String url = null;
		String channelId = null;
		
		if(helper.channelNameIsUnique(channelName)) {
			url = "https://slack.com/api/channels.create?token="+
					Helper.slackProps.get("client_token") + "&name=" + channelName;
		}
		else {
			channelId = helper.getChannelId(channelName);
			url = "https://slack.com/api/channels.unarchive?token="+
					Helper.slackProps.get("client_token") + "&channel=" + channelId;
		}
		
		String response = restTemplate.getForObject(url, String.class);
		channelId = helper.getChannelId(channelName);
		System.out.println("new channel id: " + channelId);
		
		return response;
	}
	
	@PostMapping("update/{complex}/{unit}/{new-name}")
	public String updateUnit(@PathVariable("complex") String complex, 
			@PathVariable("unit") String unit, @PathVariable("new-name") String newName) {
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		String url = "https://slack.com/api/channels.rename?token=" + 
			Helper.slackProps.get("client_token") + "&channel=" + channelId + 
			"&name=" + newName;
		
		String response = restTemplate.getForObject(url, String.class);
		return response;
	}
	
	@PostMapping("delete/{complex}/{unit}")
	public String deleteUnit(@PathVariable("complex") String complex, 
			@PathVariable("unit") String unit) {
		
		String channelName = complex + unit;
		String channelId = helper.getChannelId(channelName);
		String url = "https://slack.com/api/channels.archive?token="+
				Helper.slackProps.get("client_token") + "&channel=" + channelId;
		
		String response = restTemplate.getForObject(url, String.class);
		return response;
	}
	
	public void createFallback() {
		System.out.println("Reached create fallback");
	}
}
