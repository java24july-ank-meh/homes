package com.revature.application.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.revature.application.model.Associate;
import com.revature.application.model.ResidentUnitComplexOffice;
import com.revature.application.model.Unit;

@RestController
@RequestMapping("residentcomposite")
public class ResidentCompositeController {
	
	private String baseurl = "http://192.168.0.43:8085/api/";

	@GetMapping(value = "residentinfo")
	public ResponseEntity<Object> getResidentInfo() {
		RestTemplate restTemplate = new RestTemplate();
		Gson gson = new Gson();

		//		Associate[] jsonArr = gson.fromJson(getJsonFromService(ASSOCIATESERV+"associates"), Associate[].class);
		//Associate[] jsonArr = gson.fromJson(jsonReturned("associates", ""), Associate[].class);
		Associate[] associates = restTemplate.getForEntity(baseurl + "associates/associates", Associate[].class).getBody();

		return new ResponseEntity<Object>(associates, HttpStatus.OK);
	}

	@GetMapping("residentinfo/withRoomDetails")
	public ResponseEntity<Object> findAllAssociatesWithRoomDetails() {
		RestTemplate restTemplate = new RestTemplate();
		Gson gson = new Gson();
//		Associate[] associates = gson.fromJson(getJsonFromService(ASSOCIATESERV+"associates"), Associate[].class);
		//Associate[] associates = gson.fromJson(jsonReturned("associates", ""), Associate[].class);
		Associate[] associates = restTemplate.getForEntity(baseurl + "associates/associates", Associate[].class).getBody();
		Unit[] units = restTemplate.getForEntity(baseurl + "complex/unit", Unit[].class).getBody();
		
		List<ResidentUnitComplexOffice> residentInfoList = new ArrayList<>();

		for(Associate associate : associates) {
			ResidentUnitComplexOffice residentInfo = new ResidentUnitComplexOffice();
			residentInfo.setAssociate(associate);
			for (Unit unit : units) {			
				if (associate.getUnitId() == unit.getUnitId()) {
					residentInfo.setUnit(unit);
					residentInfo.setComplex(residentInfo.getUnit().getComplex());
					if (residentInfo.getComplex() != null)
						residentInfo.setOffice(residentInfo.getUnit().getComplex().getOffice());
				}
			}
			residentInfoList.add(residentInfo);
			/*if (associate.getUnitId() != null) {
				Unit unit = restTemplate.getForObject(baseurl + "complex/unit/"+associate.getUnitId(), Unit.class);
				ResidentUnitComplexOffice residentInfo = new ResidentUnitComplexOffice();
				residentInfo.setAssociate(associate);
				residentInfo.setUnit(
						//gson.fromJson(getJsonFromService(COMPLEXSERV+"unit/"+associate.getUnitId().toString()), Unit.class)
						//gson.fromJson(getJsonFromService(("unit/" + associate.getUnitId().toString()), ""), Unit.class)
						unit
						);
				if (residentInfo.getUnit() != null) {
					residentInfo.setComplex(residentInfo.getUnit().getComplex());
					if (residentInfo.getComplex() != null)
						residentInfo.setOffice(residentInfo.getUnit().getComplex().getOffice());
				}
	
				residentInfoList.add(residentInfo);
			}*/
		}

		return ResponseEntity.ok(residentInfoList);
	}

}