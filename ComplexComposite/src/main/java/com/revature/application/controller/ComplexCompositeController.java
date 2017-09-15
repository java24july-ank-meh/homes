package com.revature.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@RestController
@RequestMapping("complexcomposite")
public class ComplexCompositeController {
	
	
	//this is for getting the big list of commplexes
	@GetMapping("")
	public ResponseEntity<Object> getComplexes(@PathVariable("id") String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex");
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associate");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		
		//counting occupancy
		// get all of the units and
		Map<String, Integer> unitMap = new HashMap<>();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		//then based on associates unit id add to the units to get occupancy
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (Map.Entry<String,JsonElement> associateEntry : associateJson.entrySet()) {
			String associateUnitId = associateEntry.getValue().getAsJsonObject().get("unitId").getAsString();
		    unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
		}
		//add an occupancy paramater for each unit
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
			unitEntry.setValue(unitEntryJson);
		}
		
		//so now we need to add a total occupancy to the complexes which we can do withhhh unitmap still
		for (Map.Entry<String,JsonElement> complexEntry : compositeObj.entrySet()) {
			JsonObject complexEntryJson = complexEntry.getValue().getAsJsonObject();
			int complexOccupancy = 0;
			int complexCapacity = 0;
			JsonArray complexUnits = new JsonArray();
			for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
				JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
				if (complexEntryJson.get("complexId").getAsInt() == unitEntryJson.get("complexId").getAsInt()) {
					complexOccupancy += unitEntryJson.get("occupancy").getAsInt();
					complexCapacity += unitEntryJson.get("capacity").getAsInt();
					complexUnits.add(unitJson);
				}
			}
			//each complex now has information on its own occupancy and capacity and its units
			complexEntryJson.addProperty("occupancy", complexOccupancy);
			complexEntryJson.addProperty("capacity", complexCapacity);
			complexEntryJson.add("units", complexUnits);
			complexEntry.setValue(complexEntryJson);
		}

		return ResponseEntity.ok(compositeObj.toString());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getComplex(@PathVariable("id") String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex/"+id);
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associate");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		
		return null;
		
	}
	
	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8090/associates");
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
}
