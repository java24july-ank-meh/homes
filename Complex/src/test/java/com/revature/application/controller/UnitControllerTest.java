package com.revature.application.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.model.Unit;
import com.revature.application.service.UnitService;

//@RunWith(SpringRunner.class)
public class UnitControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	//Try with mocking the complex service and the office service
	@Mock
	private UnitService unitService;
	
	@InjectMocks
	private UnitController unitController;
	
	private ArrayList<Unit> units;
	
	Office o1 = new Office("address1", "phone1", "website1", "timezone1");
	Office o2 = new Office("address2", "phone2", "website2", "timezone2");
	Complex c1 = new Complex(o1.getWebsite(), "email1", o1.getPhone(), "name1", "abbr1", o1.getAddress(), "parking1", "photoUrl1", o1);
	Complex c2= new Complex(o2.getWebsite(), "email2", o2.getPhone(), "name2", "abbr2", o2.getAddress(), "parking2", "photoUrl2", o2);
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(unitController).build();
		
		
		o1.setOfficeId(2);
		
		o2.setOfficeId(3);
		
		c1.setComplexId(4);
		
		c2.setComplexId(5);
		
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
	public void testDisplayUnit() throws Exception{
		
		when(unitService.findByUnitId(units.get(0).getUnitId())).thenReturn(units.get(0));
		
		mockMvc.perform(get("/unit/0"))
			.andExpect(jsonPath("$.unitNumber",Matchers.is(units.get(0).getUnitNumber())))
			.andExpect(jsonPath("$.buildingNumber",Matchers.is(units.get(0).getBuildingNumber())))
			.andExpect(jsonPath("$.capacity",Matchers.is(units.get(0).getCapacity())))
			.andExpect(jsonPath("$.gender",Matchers.is(units.get(0).getGender())));
	
	}

@Test
	public void testCreateUnit() throws Exception{
		/*Office o3 = new Office("address3", "phone3", "website3", "timezone3");
		offices.add(o3);
		
		Complex c3 = new Complex(o3.getWebsite(), "email3", o3.getPhone(), "name3", "abbr3", o3.getAddress(), "parking3", "photoUrl2", o3);
		complexes.add(c3);*/
	
		Unit u3 = new Unit("unitNumber1","buildingNumber1",6,"gender1", null);
		u3.setUnitId(123);
		units.add(u3);
		Gson gson = new Gson();
        String json = gson.toJson(u3);
        
        when(unitService.save(any(Unit.class))).thenReturn(u3.getUnitId());
	    mockMvc.perform(
	            post("/unit")
	                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                    .content(json))
	            .andExpect(status().isCreated())
	            .andDo(print())
	            .andReturn();
	    verify(unitService, times(1)).save(u3);
	    verifyNoMoreInteractions(u3);
        
		
		/*when(unitService.save()).thenReturn(u3.getUnitId());
		
		mockMvc.perform(post("/unit"))
		.andExpect(jsonPath("$[0].unitId", Matchers.is(u3.getUnitId())))
		.andExpect(jsonPath("$[0].unitNumber",Matchers.is(u3.getUnitNumber())))
		.andExpect(jsonPath("$[0].buildingNumber",Matchers.is(u3.getBuildingNumber())))
		.andExpect(jsonPath("$[0].capacity",Matchers.is(u3.getCapacity())))
		.andExpect(jsonPath("$[0].gender",Matchers.is(u3.getGender())))
		.andDo(print());*/

	} 

	@Test
	public void testUpdateUnit() throws Exception {
		/*Office o1 = new Office("address2", "phone2", "website2", "timezone2");
		Complex c1 = new Complex("website.com", "email1", "407-090-9878", "name1", "abbr1", "2333 NW tin street", "parking1", "photoUrl1", o1);*/
		Unit u1 = new Unit("asdf", "asdfg",5,"male",null);
		/*o1.setOfficeId(2);
		c1.setComplexId(7);*/
		u1.setUnitId(1);
		Gson gson = new Gson();
        String json = gson.toJson(u1);
        
        when(unitService.findByUnitId(u1.getUnitId())).thenReturn(u1);
		when(unitService.save(u1)).thenReturn(u1.getUnitId());
		mockMvc.perform(put("/unit/{id}", u1.getUnitId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(json))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		verify(unitService, times(1)).findByUnitId(u1.getUnitId());
	    verify(unitService, times(1)).save(u1);
	    verifyNoMoreInteractions(unitService);
		
	}

	@Test
	public void testDeleteUnit() throws Exception {
		
		when(unitService.delete(units.get(0).getUnitId())).thenReturn(true);
		
		mockMvc.perform(delete("/unit/0"))
//		.andExpect(jsonPath("$.unitId", Matchers.is(units.get(0))))
//		.andExpect(jsonPath("$.unitNumber",Matchers.is(units.get(0).getUnitNumber())))
//		.andExpect(jsonPath("$.buildingNumber",Matchers.is(units.get(0).getBuildingNumber())))
//		.andExpect(jsonPath("$.capacity",Matchers.is(units.get(0).getCapacity())))
//		.andExpect(jsonPath("$.gender",Matchers.is(units.get(0).getGender())))
		;
		}

}
