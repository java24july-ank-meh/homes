package com.revature.application.controller;

import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.service.ComplexService;

public class ComplexControllerTest {

	@Mock
	ComplexService cs;
	
	
	@Before
	public void setUp() throws Exception {
		
		Office o1 = new Office("address1", "phone1", "website1", "timezone1");
		Office o2 = new Office("address2", "phone2", "website2", "timezone2");
		
		
		List<Complex> complexes = new ArrayList<Complex>();
		Complex c1 = new Complex(o1.getWebsite(), "email1", o1.getPhone(), "name1", "abbr1", o1.getAddress(), "parking1", "photoUrl1", o1);
		Complex c2= new Complex(o2.getWebsite(), "email2", o2.getPhone(), "name2", "abbr2", o2.getAddress(), "parking2", "photoUrl2", o2);
		
		
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUnits() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateComplex() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteComplex() {
		fail("Not yet implemented");
	}

}
