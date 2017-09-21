package com.revature.application.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.model.Unit;
import com.revature.application.service.UnitService;

public class UnitControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Mock
	private UnitService unitService;
	
	@InjectMocks
	private UnitController unitController;
	
	private ArrayList<Unit> units;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(unitController).build();
		
		Office o1 = new Office("address1", "phone1", "website1", "timezone1");
		Office o2 = new Office("address2", "phone2", "website2", "timezone2");
		Complex c1 = new Complex(o1.getWebsite(), "email1", o1.getPhone(), "name1", "abbr1", o1.getAddress(), "parking1", "photoUrl1", o1);
		Complex c2= new Complex(o2.getWebsite(), "email2", o2.getPhone(), "name2", "abbr2", o2.getAddress(), "parking2", "photoUrl2", o2);
		
		units = new ArrayList<Unit>();
		units.add(new Unit("unitNumber1","buildingNumber1",6,"gender1",c1));
		units.add(new Unit("unitNumber2","buildingNumber2",6,"gender2",c2));	
	}
	
	@Test
	public void testDisplayAllUnit() throws Exception{
		when(unitService.findAll()).thenReturn(units);
		
		mockMvc.perform(get("/unit"))
			.andExpect(jsonPath("$", Matchers.hasSize(2)))
			.andExpect(jsonPath("$[0].unitNumber",Matchers.is(units.get(0).getUnitNumber())))
			.andExpect(jsonPath("$[0].buildingNumber",Matchers.is(units.get(0).getBuildingNumber())))
			.andExpect(jsonPath("$[0].capacity",Matchers.is(units.get(0).getCapacity())))
			.andExpect(jsonPath("$[0].gender",Matchers.is(units.get(0).getGender())))
			.andExpect(jsonPath("$[1].unitNumber",Matchers.is(units.get(1).getUnitNumber())))
			.andExpect(jsonPath("$[1].buildingNumber",Matchers.is(units.get(1).getBuildingNumber())))
			.andExpect(jsonPath("$[1].capacity",Matchers.is(units.get(1).getCapacity())))
			.andExpect(jsonPath("$[1].gender",Matchers.is(units.get(1).getGender())));	
	}

	@Test
	public void testDisplayUnit() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUnit() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUnit() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUnit() {
		fail("Not yet implemented");
	}

}
