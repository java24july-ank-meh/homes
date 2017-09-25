package com.revature.application.controller;

import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.revature.application.model.Complex;
import com.revature.application.model.Office;
import com.revature.application.model.Unit;
import com.revature.application.service.ComplexService;

public class ComplexControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
	
	@Mock
	ComplexService mockCS;
	@InjectMocks
    private ComplexController complexController;
	
	List<Office> offices;
	List<Complex> complexes;
	List<Unit> units1;
	List<Unit> units2;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
				
		this.mockMvc = MockMvcBuilders.standaloneSetup(complexController).build();

		offices = new ArrayList<Office>();
		Office o1 = new Office("address1", "phone1", "website1", "timezone1");
		o1.setOfficeId(0);
		Office o2 = new Office("address2", "phone2", "website2", "timezone2");
		o2.setOfficeId(1);
		
		offices.add(o1);
		offices.add(o2);
		
		complexes = new ArrayList<Complex>();
		Complex c1 = new Complex(o1.getWebsite(), "email1", o1.getPhone(), "name1", "abbr1", o1.getAddress(), "parking1", "photoUrl1", o1);
		c1.setComplexId(0);
		Complex c2= new Complex(o2.getWebsite(), "email2", o2.getPhone(), "name2", "abbr2", o2.getAddress(), "parking2", "photoUrl2", o2);
		c2.setComplexId(1);
		
		units1 = new ArrayList<Unit>();
		Unit u11 = new Unit("unitNumber11","buildingNumber11",6,"gender1",c1);
		u11.setUnitId(0);
		Unit u12 = new Unit("unitNumber12","buildingNumber11",6,"gender1",c1);	
		u12.setUnitId(1);
		units1.add(u11);
		units1.add(u12);
		
		c1.setUnits(units1);
		
		units2 = new ArrayList<Unit>();
		Unit u21 = new Unit("unitNumber21","buildingNumber22",4,"gender1",c2);
		u21.setUnitId(3);
		Unit u22 = new Unit("unitNumber22","buildingNumber21",4,"gender2",c2);	
		u22.setUnitId(4);
		units2.add(u21);
		units2.add(u22);
		
		c2.setUnits(units2);
		
		complexes.add(c1);
		complexes.add(c2);		
		
	}

//	@Test
	public void testFindAll() throws Exception {
		when(mockCS.findAll()).thenReturn(complexes);
		
		mockMvc.perform(get("/complex"))
			.andExpect(jsonPath("$", Matchers.hasSize(2)))
			.andExpect(jsonPath("$[0].complexId",Matchers.is(complexes.get(0).getComplexId())))
			.andExpect(jsonPath("$[0].name",Matchers.is(complexes.get(0).getName())))
			.andExpect(jsonPath("$[0].website",Matchers.is(complexes.get(0).getWebsite())))
			.andExpect(jsonPath("$[0].photoUrl",Matchers.is(complexes.get(0).getPhotoUrl())))
			.andExpect(jsonPath("$[1].complexId",Matchers.is(complexes.get(1).getComplexId())))
			.andExpect(jsonPath("$[1].name",Matchers.is(complexes.get(1).getName())))
			.andExpect(jsonPath("$[1].website",Matchers.is(complexes.get(1).getWebsite())))
			.andExpect(jsonPath("$[1].photoUrl",Matchers.is(complexes.get(1).getPhotoUrl())))
//			.andDo(print())
			;
		
	}

//	@Test
	public void testFindOne() throws Exception {
	when(mockCS.findByComplexId(complexes.get(0).getComplexId())).thenReturn(complexes.get(0));
		
		mockMvc.perform(get("/complex/0"))
			.andExpect(jsonPath("$.complexId",Matchers.is(complexes.get(0).getComplexId())))
			.andExpect(jsonPath("$.name",Matchers.is(complexes.get(0).getName())))
			.andExpect(jsonPath("$.website",Matchers.is(complexes.get(0).getWebsite())))
			.andExpect(jsonPath("$.photoUrl",Matchers.is(complexes.get(0).getPhotoUrl())))
//			.andDo(print())
			;
	}

//	@Test
	public void testFindUnits() throws Exception {
		when(mockCS.findByComplexId(complexes.get(0).getComplexId())).thenReturn(complexes.get(0));
		
		mockMvc.perform(get("/complex/0/units"))
//		.andExpect(jsonPath("$", Matchers.hasSize(2)))
		.andExpect(jsonPath("$[0].unitId",Matchers.is((int)units1.get(0).getUnitId())))
		.andExpect(jsonPath("$[0].unitNumber",Matchers.is(units1.get(0).getUnitNumber())))
		.andExpect(jsonPath("$[0].buildingNumber",Matchers.is(units1.get(0).getBuildingNumber())))
		.andExpect(jsonPath("$[0].capacity",Matchers.is(units1.get(0).getCapacity())))
		.andExpect(jsonPath("$[0].gender",Matchers.is(units1.get(0).getGender())))
		.andExpect(jsonPath("$[1].unitId",Matchers.is((int)units1.get(1).getUnitId())))
		.andExpect(jsonPath("$[1].unitNumber",Matchers.is(units1.get(1).getUnitNumber())))
		.andExpect(jsonPath("$[1].buildingNumber",Matchers.is(units1.get(1).getBuildingNumber())))
		.andExpect(jsonPath("$[1].capacity",Matchers.is(units1.get(1).getCapacity())))
		.andExpect(jsonPath("$[1].gender",Matchers.is(units1.get(1).getGender())))
//		.andDo(print())
		;
		
	}

	@Test
	public void testCreateComplex() throws Exception {
				
		Complex c3 = new Complex("websit", "email2", "phone", "name2", "abbr2", "address", "parking2", "photoUrl2", null);
		c3.setComplexId(2);
		Gson gson = new Gson();
		String json = gson.toJson(c3);
		
		when(mockCS.save(c3)).thenReturn(c3.getComplexId());
		 mockMvc.perform(
		            post("/complex")
		                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		                    .content(json))
		            .andExpect(status().isOk())
//		            .andDo(print())
		            .andReturn();
		    verify(mockCS, times(1)).save(any(Complex.class));
		    verifyNoMoreInteractions(mockCS);
	}

//	@Test
	public void testUpdateComplex() throws Exception {
		Complex c1 = new Complex("website1","email","phone","name","abbr","address","parking","photo",null);
		c1.setComplexId(1);
		Gson gson = new Gson();
        String json = gson.toJson(c1);
        
        when(mockCS.findByComplexId(c1.getComplexId())).thenReturn(c1);
		when(mockCS.save(c1)).thenReturn(c1.getComplexId());
		mockMvc.perform(put("/unit/{id}", c1.getComplexId())
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		        .content(json))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		verify(mockCS, times(1)).findByComplexId(c1.getComplexId());
	    verify(mockCS, times(1)).save(c1);
	    verifyNoMoreInteractions(mockCS);
		
		
		
	}

//	@Test
	public void testDeleteComplex() throws Exception {
		when(mockCS.delete(complexes.get(1).getComplexId())).thenReturn(true);
		
		mockMvc.perform(delete("/complex/1"))
		.andExpect(status().isOk());
//		.andDo(print())
		verify(mockCS, times(1)).delete(complexes.get(1).getComplexId());
	    verifyNoMoreInteractions(mockCS);
		;
	}

}
