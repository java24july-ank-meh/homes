package com.revature.application.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Service
public class ComplexCompositeService {

	public JsonObject getAllComplexes() {
		
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex");
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associate");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		
		//counting occupancy
		// get all of the units and store in a map so that way i can use ids as keys
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
		
		//so now we need to add a total occupancy to the complexes
		for (Map.Entry<String,JsonElement> complexEntry : compositeObj.entrySet()) {
			JsonObject complexEntryJson = complexEntry.getValue().getAsJsonObject();
			int complexOccupancy = 0;
			int complexCapacity = 0;
			//JsonArray complexUnits = new JsonArray();
			for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
				JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
				if (complexEntryJson.get("complexId").getAsInt() == unitEntryJson.get("complexId").getAsInt()) {
					complexOccupancy += unitEntryJson.get("occupancy").getAsInt();
					complexCapacity += unitEntryJson.get("capacity").getAsInt();
					//complexUnits.add(unitJson);
				}
			}
			//each complex now has information on its own occupancy and capacity and its units
			complexEntryJson.addProperty("occupancy", complexOccupancy);
			complexEntryJson.addProperty("capacity", complexCapacity);
			//complexEntryJson.add("units", complexUnits);
			complexEntry.setValue(complexEntryJson);
		}
		
		return compositeObj;
	}
	
	public JsonObject getComplex(String id) {
		JsonObject compositeObj = getJsonFromService("http://localhost:8093/complex/"+id);
		JsonObject associateJson = getJsonFromService("http://localhost:8090/associate");
		JsonObject unitJson = getJsonFromService("http://localhost:8093/unit");
		
		//unit map so i can assign occupancies based on unitids as keys
		//exclude units that are not in this complex
		Map<String, Integer> unitMap = new HashMap<>();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			if (unitEntryJson.get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt())
				unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		
		//then based on associates unit id add to the units to get occupancy
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (Map.Entry<String,JsonElement> associateEntry : associateJson.entrySet()) {
			String associateUnitId = associateEntry.getValue().getAsJsonObject().get("unitId").getAsString();
		    unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
		}
		
		//give each unit an occupancy and add a unit to a json array
		JsonArray complexUnits = new JsonArray();
		for (Map.Entry<String,JsonElement> unitEntry : unitJson.entrySet()) {
			JsonObject unitEntryJson = unitEntry.getValue().getAsJsonObject();
			if (unitEntryJson.get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt()) {
				unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
				complexUnits.add(unitEntryJson);
			}
			unitEntry.setValue(unitEntryJson);
	    }
		//give array of units to composite obj with the occupancies.
		//TODO make the array earlier
		compositeObj.add("units", complexUnits);
		
		return compositeObj;
	}
	
	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
}
