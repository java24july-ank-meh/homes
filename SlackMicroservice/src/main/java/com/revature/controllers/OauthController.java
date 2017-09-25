package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("manager")
public class OauthController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Helper helper;
	
	@PostMapping("scopes/basic")
	public Object getBasicScopes(@RequestBody String body, HttpSession session) throws IOException {
		String code =  "";
		JsonObject jobj = new JsonObject();
		JSONObject json = null;
		try {
			json = new JSONObject(body);
			code = json.getString("code");
		}catch(JSONException e) {
			e.printStackTrace();
		} finally {
			if(code.equals("")) {
				jobj.addProperty("scope", "");
				return ResponseEntity.ok(jobj.toString());
			}
		}

		String clientId = "237895291120.241002501221";
		String clientSecret = "79c83d653ca6fb2dcd6b74d79bfa6cfc";
		// get parameters client_id, client_secret,code to retrieve token
		String requestUrl = "https://slack.com/api/oauth.access";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
			params.add("code", code);
			params.add("client_id", clientId);
			params.add("client_secret", clientSecret);
		
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
			HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, request, String.class);
		
		String token = "";
		String email ="";
		String user = "";
		try {
			json = new JSONObject(response.getBody());
			token = json.getString("access_token");
			email = json.getJSONObject("user").getString("email");
			user = json.getString("user");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//check if user is admin
		if(!helper.isAdmin(token, email)) {
			jobj.addProperty("scope", "all");
			return ResponseEntity.ok(user);
		}
		
		//send user to authorize channel and im scopes
		String scopes = helper.scopes(token);
		if(!scopes.contains("channels:write")) {
			jobj.addProperty("scope", "basic");
			return ResponseEntity.ok(jobj.toString());
		}
		//send user to authorize client scope
		if(!scopes.contains("client")) {
			jobj.addProperty("scope", "client");
			return ResponseEntity.ok(jobj.toString());
		}
		//send manager to application
		session.setAttribute("token", token);
		return ResponseEntity.ok(user);
		
	}

}
