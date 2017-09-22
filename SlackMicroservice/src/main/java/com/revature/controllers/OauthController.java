package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("manager")
public class OauthController {
	
	@Autowired
	Helper helper;
	
	@GetMapping("scopes/basic")
	public Object getBasicScopes(HttpSession http) throws IOException {
		SecurityContext sc = (SecurityContextImpl) http.getAttribute("SPRING_SECURITY_CONTEXT");
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) sc.getAuthentication().getDetails();
		String token =  details.getTokenValue();
		RestTemplate template = new RestTemplate();
		String scopes = helper.scopes(token);
		JsonObject jobj = new JsonObject();
		if(!scopes.contains("channels:write")) {
			jobj.addProperty("scope", "basic");
			return ResponseEntity.ok(jobj.toString());
		}
		if(!scopes.contains("client")) {
			jobj.addProperty("scope", "client");
			return ResponseEntity.ok(jobj.toString());
		}
		jobj.addProperty("scope", "all");
		return ResponseEntity.ok(jobj.toString());
	}

}
