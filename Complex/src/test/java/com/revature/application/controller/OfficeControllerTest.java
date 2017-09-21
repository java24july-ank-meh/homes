package com.revature.application.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.revature.application.model.Office;
import com.revature.application.service.OfficeService;

@RunWith(SpringJUnit4ClassRunner.class)
public class OfficeControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock // Change the mock import later might be wrong
	private OfficeService officeServiceMock;
	
	@InjectMocks
	OfficeController officeControllerMock;
	
	@Before
	public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(officeControllerMock).build();
    }
	
	@Test
	public void getAllOfficeTest() throws Exception {
		List<Office> mockOffice = Arrays.asList(new Office ("2090 SE 78 CT", "987-912-9087","www.mockOffice1.com", "EST"), 
					new Office ("780 E Plaza Street", "910-839-0001","www.mockOfficeTwo.com", "EST"), 
					new Office ("105460 W Damn Son", "120-789-2304","www.therentistoohigh.com", "PST"));
		when(officeServiceMock.findAll()).thenReturn(mockOffice);
		
		mockMvc.perform(get("http://localhost:8093/office")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$[0].address", is("2090 SE 78 CT")))
        .andExpect(jsonPath("$[0].phone", is("987-912-9087")))
        .andExpect(jsonPath("$[0].website", is("www.mockOffice1.com")))
        .andExpect(jsonPath("$[0].timezone", is("EST")))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$[1].address", is("780 E Plaza Street")))
        .andExpect(jsonPath("$[1].phone", is("910-839-0001")))
        .andExpect(jsonPath("$[1].website", is("www.mockOfficeTwo.com")))
        .andExpect(jsonPath("$[1].timezone", is("EST")))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$[2].address", is("105460 W Damn Son")))
        .andExpect(jsonPath("$[2].phone", is("120-789-2304")))
        .andExpect(jsonPath("$[2].website", is("www.therentistoohigh.com")))
        .andExpect(jsonPath("$[2].timezone", is("PST")));
		verify(officeServiceMock, times(1)).findAll();
	    verifyNoMoreInteractions(officeServiceMock);
	}
	
	@Test
	public void getOfficeByOfficeIdTest() throws Exception{
		Office mockOffice2 = new Office("4907 SW The Web Street", "240-878-0091", "www.theapartmentoffice.com", "CST");
	
		when(officeServiceMock.find(1)).thenReturn(mockOffice2);
		mockMvc.perform(get("http://localhost:8093/office/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		verify(officeServiceMock, times(1)).find(1);
	    verifyNoMoreInteractions(officeServiceMock);
	}
	
	@Test
	public void createOfficeTest() throws Exception{
		Office mockApartment4 = new Office("23T S The Web Ave", "910-878-9178", "www.theapartmentoffice.com", "CST");
		Gson gson = new Gson();
        String json = gson.toJson(mockApartment4);
		
        doNothing().when(officeServiceMock).save(mockApartment4);
	    mockMvc.perform(
	            post("http://localhost:8093/office/")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(json))
	            .andExpect(status().isCreated())
	            .andExpect(header().string("location", containsString("http://localhost/users/")));
	    verify(officeServiceMock, times(1)).save(mockApartment4);
	    verifyNoMoreInteractions(officeServiceMock);
		
	}
	
	
	

	/*@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindComplexes() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}
*/
}
