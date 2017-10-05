package com.revature.application.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.revature.application.model.Associate;
import com.revature.application.model.Complex;
import com.revature.application.model.Notification;
import com.revature.application.model.Unit;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

@Service
public class ComplexCompositeService {

	private String baseurl = "http://107.23.6.219.com:8085/";
	
	/*public JsonArray allComplexes(){
		return getJsonArrayFromService("http://107.22.129.162:8093/complex");
	}*/
	
	public JsonArray allUnits() {
		JsonArray units = getJsonArrayFromService(baseurl + "complex/unit");
		JsonArray associates = getJsonArrayFromService(baseurl + "associates/associates");
		
		Map<Long, JsonArray> AssociatesForEachUnit = new HashMap<>();
		for(int i = 0; i < units.size(); i++) {
			JsonObject unit = units.get(i).getAsJsonObject();
			Long unitId = unit.get("unitId").getAsLong();
			AssociatesForEachUnit.put(unitId, new JsonArray());
		}
		
		for(int i = 0; i < associates.size(); i++) {
			JsonObject associate = associates.get(i).getAsJsonObject();
			if(associate.get("unitId").isJsonNull()) continue;
			Long unitId = associate.get("unitId").getAsLong();
			JsonArray unitAssociates = AssociatesForEachUnit.get(unitId);
			if(unitAssociates == null) continue;
			unitAssociates.add(associate);
		}
		
		System.out.println(AssociatesForEachUnit);
		
		for(int i = 0; i < units.size(); i++) {
			JsonObject unit = units.get(i).getAsJsonObject();
			Long unitId = unit.get("unitId").getAsLong();
			unit.add("residents", AssociatesForEachUnit.get(unitId));
		}
		
		System.out.println(units);
		
		return units;
	}
	
	public JsonObject getAllComplexes() {
		
		RestTemplate restTemplate = new RestTemplate();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();

		Complex[] complexes = restTemplate.getForEntity(baseurl + "complex/complex", Complex[].class).getBody();
		Associate[] associates = restTemplate.getForEntity(baseurl + "associates/associates", Associate[].class).getBody();
		Unit[] units = restTemplate.getForEntity(baseurl + "complex/unit", Unit[].class).getBody();
		
		JsonObject compositeObj = new JsonObject();
		JsonArray complexJson = jsonParser.parse(gson.toJson(complexes)).getAsJsonArray();
		JsonArray unitJson = jsonParser.parse(gson.toJson(units)).getAsJsonArray();
		JsonArray associateJson = jsonParser.parse(gson.toJson(associates)).getAsJsonArray();
		
		// get all of the units and store in a map so that way i can use ids as keys
		Map<String, Integer> unitMap = new HashMap<>();
		for (JsonElement unitEntry : unitJson) {
			JsonObject unitEntryJson = unitEntry.getAsJsonObject();
			unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		//then based on associates unit id add to the units to get occupancy	
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (JsonElement associateEntry : associateJson) {
			if (associateEntry.getAsJsonObject().get("unitId") != null) {
				String associateUnitId = associateEntry.getAsJsonObject().get("unitId").getAsString();
				if (unitMap.containsKey(associateUnitId))
					unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
			}
		}
		//add an occupancy paramater for each unit
		for (JsonElement unitEntry : unitJson) {
			JsonObject unitEntryJson = unitEntry.getAsJsonObject();
			unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
			unitEntry = unitEntryJson;
		}
		
		//so now we need to add a total occupancy to the complexes
		for (JsonElement complexEntry : complexJson) {
			JsonObject complexEntryJson = complexEntry.getAsJsonObject();
			int complexOccupancy = 0;
			int complexCapacity = 0;
			JsonArray complexUnits = new JsonArray();
			for (JsonElement unitEntry : unitJson) {
				JsonObject unitEntryJson = unitEntry.getAsJsonObject();
				//System.out.println(unitEntryJson.getAsJsonObject("complex").get("complexId"));
				if (unitEntryJson.getAsJsonObject("complex") != null && complexEntryJson.get("complexId").getAsInt() == unitEntryJson.getAsJsonObject("complex").get("complexId").getAsInt()) {
					complexOccupancy += unitEntryJson.get("occupancy").getAsInt();
					complexCapacity += unitEntryJson.get("capacity").getAsInt();
					complexUnits.add(unitEntry);
				}
			}
			//each complex now has information on its own occupancy and capacity and its units
			complexEntryJson.addProperty("occupancy", complexOccupancy);
			complexEntryJson.addProperty("capacity", complexCapacity);
			complexEntryJson.add("units", complexUnits);
			complexEntry = complexEntryJson;
		}
		compositeObj.add("complexes", complexJson);
		return compositeObj;
	}
	
	public JsonObject getComplex(String id) {
		
		RestTemplate restTemplate = new RestTemplate();
		JsonParser jsonParser = new JsonParser();
		Gson gson = new Gson();

		Complex complex = restTemplate.getForObject(baseurl + "complex/complex/"+id, Complex.class);
		Associate[] associates = restTemplate.getForEntity(baseurl + "associates/associates", Associate[].class).getBody();
		Unit[] units = restTemplate.getForEntity(baseurl + "complex/unit", Unit[].class).getBody();
		
		JsonObject compositeObj = jsonParser.parse(gson.toJson(complex)).getAsJsonObject();
		JsonArray unitJson = jsonParser.parse(gson.toJson(units)).getAsJsonArray();
		JsonArray associateJson = jsonParser.parse(gson.toJson(associates)).getAsJsonArray();
		
		//unit map so i can assign occupancies based on unitids as keys
		//exclude units that are not in this complex
		Map<String, Integer> unitMap = new HashMap<>();
		for (JsonElement unitEntry : unitJson) {
			JsonObject unitEntryJson = unitEntry.getAsJsonObject();
			if (unitEntryJson.get("complex") != null && unitEntryJson.getAsJsonObject("complex").get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt())
				unitMap.put(unitEntryJson.get("unitId").getAsString(), 0);
	    }
		
		//then based on associates unit id add to the units to get occupancy
		//TODO: combine with bottom for loop so that residents can be added an arryay?
		for (JsonElement associateEntry : associateJson) {
			String associateUnitId = associateEntry.getAsJsonObject().get("unitId").getAsString();
			if (unitMap.containsKey(associateUnitId))
				unitMap.put(associateUnitId, unitMap.get(associateUnitId) +1);
		}
		
		//give each unit an occupancy and add a unit to a json array
		JsonArray complexUnits = new JsonArray();
		for (JsonElement unitEntry : unitJson) {
			JsonObject unitEntryJson = unitEntry.getAsJsonObject();
			if (unitEntryJson.get("complex") != null && unitEntryJson.getAsJsonObject("complex").get("complexId").getAsInt() == compositeObj.get("complexId").getAsInt()) {
				unitEntryJson.addProperty("occupancy", unitMap.get(unitEntryJson.get("unitId").getAsString()));
				complexUnits.add(unitEntryJson);
			}
			unitEntry = unitEntryJson;
	    }
		//give array of units to composite obj with the occupancies.
		//TODO make the array earlier
		compositeObj.add("units", complexUnits);
		
		return compositeObj;
	}
	
	public void unitReassignment(String jsonString) {
		JsonObject reassignJson = new JsonParser().parse(jsonString).getAsJsonObject();
		if (reassignJson.get("unitId").getAsString() == null)
			reassignJson.addProperty("unitId", "unnassigned");
		String message = "Associate /associates/" + reassignJson.get("associateId").getAsString() + 
				" would like to request a housing transfer from /units/" + reassignJson.get("unitId").getAsString() +
				" to /units/" + reassignJson.get("targetUnitId").getAsString();
		Notification notification = new Notification(message);
		
		try {
			//HttpPost post = new HttpPost("http://localhost:8097/notifications/create");
			HttpPost post = new HttpPost(baseurl + "request/notifications/create");
			post.setHeader("Content-type", "application/json");
			
			StringEntity postingString = new StringEntity(new Gson().toJson(notification));
			post.setEntity(postingString);
			HttpClientBuilder.create().build().execute(post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private JsonObject getJsonFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonObject();
	}
	
	private JsonArray getJsonArrayFromService(String url) {
		Client client = Client.create();
		WebResource resource = client.resource(url);
		String associate = resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		return new JsonParser().parse(associate).getAsJsonArray();
	}

	public Object assignUnitToAssociate(int unitId, int associateId) {
		RestTemplate restTemplate = new RestTemplate();
		Associate associate = restTemplate.getForObject(baseurl + "associates/associates/"+associateId, Associate.class);
		associate.setUnitId((long) unitId);
		try {
			HttpPost post = new HttpPost(baseurl + "associates/associates/createOrUpdate");

			post.setHeader("Content-type", "application/json");
			StringEntity postingString = new StringEntity(new Gson().toJson(associate));
			post.setEntity(postingString);
			HttpClientBuilder.create().build().execute(post);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
