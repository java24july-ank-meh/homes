package com.revature;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.revature.controllers.ComplexController;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.security.Principal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class SlackMicroserviceApplicationTest{
	
	@Autowired
	RestTemplate rt;
	
	@Autowired
	ComplexController cc;
	
	@Autowired 
	WebApplicationContext wac;
	
	private MockMvc mvc;
	
	@LocalServerPort
	private int port;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void contextLoads() {
		assertThat(cc, notNullValue());
		assertThat(rt, notNullValue());
	}
	
	@Test
	public void complexControllerSecured() {

		String url = "http://localhost:8088/complex/create";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
	 
		thrown.expect(HttpClientErrorException.class);
		ResponseEntity<String> response = rt.postForEntity(url, request, String.class);
	}
	
	@Test
	public void mockApplicationUser(){
		Principal principal = mock(Principal.class);
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
		
		String url = "http://localhost:8088/complex/create";
		
		MultiValueMap<String, String> params = 
				new LinkedMultiValueMap<String, String>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		ResponseEntity<String> response = rt.postForEntity(url, request, String.class);
		
		
	}

}
