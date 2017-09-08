package com.revature.application.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.application.model.Associate;
import com.revature.application.services.AssociateService;

@RunWith(SpringRunner.class)
@WebMvcTest(AssociateController.class)
public class AssociateControllerTests {
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    private AssociateService service;
 
    @Test
    public void test() {	
    }
      

}
