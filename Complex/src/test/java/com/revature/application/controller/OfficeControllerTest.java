package com.revature.application.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        .andExpect(jsonPath("$[2].timezone", is("PST")))
        .andDo(print());
		verify(officeServiceMock, times(1)).findAll();
	    verifyNoMoreInteractions(officeServiceMock);
	}
	
	@Test
	public void getOfficeByOfficeIdTest() throws Exception{
		Office mockOffice2 = new Office("4907 SW The Web Street", "240-878-0091", "www.theapartmentoffice.com", "CST");
	
		when(officeServiceMock.find(1)).thenReturn(mockOffice2);
		mockMvc.perform(get("http://localhost:8093/office/{id}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andDo(print());
		verify(officeServiceMock, times(1)).find(1);
	    verifyNoMoreInteractions(officeServiceMock);
	}
	
	@Test
	public void createOfficeTest() throws Exception{
		Office mockOffice4 = new Office("23T S The Web Ave", "910-878-9178", "www.theapartmentoffice.com", "CST");
		mockOffice4.setOfficeId(2);
		Gson gson = new Gson();
        String json = gson.toJson(mockOffice4);
		
        //when(officeServiceMock.find(mockOffice4.getOfficeId())).thenReturn(mockOffice4);
        when(officeServiceMock.save(any(Office.class))).thenReturn(mockOffice4.getOfficeId());
	    mockMvc.perform(
	            post("/office")
	                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                    .content(json)) //Include the 
	            .andExpect(status().isOk())
	            //.andExpect(header().string("location", containsString("http://localhost/office/")))
	            .andDo(print())
	            .andReturn();
	    verify(officeServiceMock, times(1)).save(any(Office.class));
	    verifyNoMoreInteractions(officeServiceMock);


		
	}
	
	/*private byte[] objectToJson(Office mockOffice4) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Test 
	public void updateOfficeTest() throws Exception{
		Office mockOffice5 = new Office("23T S The Web Ave", "893-119-9945", "www.theapartmentoffice.com", "CST");
		Gson gson = new Gson();
        String json = gson.toJson(mockOffice5);
		when(officeServiceMock.find(mockOffice5.getOfficeId())).thenReturn(mockOffice5);
	    mockMvc.perform(
	            put("/office/{id}", mockOffice5.getOfficeId())
	                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                    .content(json))
	            .andExpect(status().isOk())
	            .andDo(print())
	        	.andReturn();
	    verify(officeServiceMock, times(1)).find(mockOffice5.getOfficeId());
	    verify(officeServiceMock, times(1)).save(mockOffice5);
	    verifyNoMoreInteractions(officeServiceMock);
	}
	
	@Test
	public void deleteApartmentTest()throws Exception{
		Office mockOffice6 = new Office ("780 E Plaza Street", "910-839-0001","www.mockOfficeTwo.com", "EST");
		mockOffice6.setOfficeId(5);
		when(officeServiceMock.find(mockOffice6.getOfficeId())).thenReturn(null);
		//doNothing().doThrow(new RuntimeException()).when(officeServiceMock).delete(mockOffice6.getOfficeId());
	    mockMvc.perform(
	            delete("/office/{id}", mockOffice6.getOfficeId()))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andReturn();
	    verify(officeServiceMock, times(1)).delete(mockOffice6.getOfficeId());
	    verifyNoMoreInteractions(officeServiceMock);
	    
	}
}
