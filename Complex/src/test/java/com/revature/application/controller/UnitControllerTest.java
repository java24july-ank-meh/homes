package com.revature.application.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.model.Unit;
import com.revature.application.service.UnitService;

public class UnitControllerTest {

	@Mock
	private UnitService unitService;
	
	@InjectMocks
	private UnitController unitController;
	
	@Before
	public void setUp() throws Exception {
		Office o1 = new Office("address1", "phone1", "website1", "timezone1");
		Office o2 = new Office("address2", "phone2", "website2", "timezone2");
		Complex c1 = new Complex(o1.getWebsite(), "email1", o1.getPhone(), "name1", "abbr1", o1.getAddress(), "parking1", "photoUrl1", o1);
		Complex c2= new Complex(o2.getWebsite(), "email2", o2.getPhone(), "name2", "abbr2", o2.getAddress(), "parking2", "photoUrl2", o2);
		
		Unit u1 = new Unit("unitNumber1","buildingNumber1",6,"gender1",c1);
		Unit u2 = new Unit("unitNumber2","buildingNumber2",6,"gender2",c2);	
	}
	
	@Test
	public void testDisplayAllUnit() {
		fail("Not yet implemented");
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
